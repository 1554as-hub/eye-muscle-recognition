package com.eyescloud.controller;




import com.eyescloud.FileHandlerUtil;
import com.eyescloud.entity.FileModle;
import com.eyescloud.entity.User;
import com.eyescloud.service.FileHandlerService;
import com.eyescloud.util.ToUserUtil;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class FileHandlerController {

    @Autowired
    private FileHandlerService fileHandlerService;

    @Autowired
    private Environment env;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private FileHandlerUtil fileHandlerUtil = new FileHandlerUtil();

    private ToUserUtil userUtil = new ToUserUtil();
    private final Logger logger = Logger.getLogger(FileHandlerController.class);


    @PostMapping("/fileUpload")
    @Transactional
    public ResponseEntity<String> test1(@RequestParam("files") MultipartFile[] files ,
                                        @AuthenticationPrincipal OAuth2Authentication oAuth2Authentication) throws Exception {
        String FILE_ROOT_PATH = env.getProperty("file.uploadFolder").replace("file:" ,"");
        User user = userUtil.getUser(oAuth2Authentication);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        String str = sdf.format(date);

        if(files != null){
            double totalSize = 0;
            for (MultipartFile file : files){
                    BigDecimal bigDecimal = new BigDecimal(file.getSize() / 1024000);
                    totalSize += bigDecimal.setScale(4 , RoundingMode.HALF_UP).doubleValue();

                    String originalFilename = file.getOriginalFilename();
                    String newFileName = null;
                    logger.info(originalFilename);
                    if(originalFilename != null && originalFilename.length() > 0){
                        newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf(".") );
                        //  ?????????????????????????????????  ???????????????????????? ??????????????????
                        File file1 = new File(FILE_ROOT_PATH+ File.separator + user.getId());
                        if(!file1.exists()) {
                            file1.mkdir();
                        }

                        //  ???????????????????????????  ????????????????????????
                        File file2 = new File(FILE_ROOT_PATH + File.separator + user.getId()
                                + File.separator + str);
                        if(!file2.exists()) {
                            file2.mkdir();
                        }

                        File newFile = new File( FILE_ROOT_PATH + File.separator + user.getId()
                                + File.separator + str + File.separator  +newFileName);
                        // ?????????????????????????????????
                        file.transferTo(newFile);

                        // ???????????????????????????
                        if(fileHandlerUtil.isZip(file.getOriginalFilename())){
                            //  ?????????????????????  ????????????????????????//userid //??????//????????? ????????????
                            //  ????????????????????????????????????
                            Thread thread = new Thread(() -> {
                                try {
                                    String sourcePath = newFile.getAbsolutePath();
                                    String desPaht = FILE_ROOT_PATH + user.getId()
                                            + File.separator + str;
                                    String cmd = env.getProperty("file.zipsoftPath");
                                    cmd = "\"" + cmd + "\"";
                                    String exec = cmd + " e " + sourcePath + " "+desPaht ;
                                    Process process = Runtime.getRuntime().exec(exec);
                                   //  ??????????????????????????????
                                    process.waitFor();
                                    //???????????????????????????????????????????????????????????????
                                    newFile.delete();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//                            FileHandlerUtil.unzip();

                            });
                            thread.start();
                        }
                    }
            }

            // ?????????????????????????????????
            FileModle fileModle = new FileModle();
            fileModle.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            fileModle.setTotalSize(totalSize);
            fileModle.setId(UUID.randomUUID().toString());
            fileModle.setStatus(0);
            fileModle.setZipPath("");
            fileModle.setName(UUID.randomUUID().toString());
            fileModle.setFilePath(user.getId()+ File.separator + str + File.separator);
            fileHandlerService.saveFile(fileModle);
            fileHandlerService.saveMiddleFile(user.getId() , fileModle.getId());
            //  ??????????????????????????????rabbitmq???
            rabbitTemplate.convertAndSend("file_handler_exchange",
                    "file.handler" , fileModle);
            //  ????????????
            return ResponseEntity.status(200).body("????????????");
        }

        return ResponseEntity.status(500).body("????????????????????? ???????????????");

    }



}
