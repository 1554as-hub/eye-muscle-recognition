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
                        //  判读是否存在用户的目录  存在则不进行操作 不存在则创建
                        File file1 = new File(FILE_ROOT_PATH+ File.separator + user.getId());
                        if(!file1.exists()) {
                            file1.mkdir();
                        }

                        //  判断是否存在该目录  不存在则进行操作
                        File file2 = new File(FILE_ROOT_PATH + File.separator + user.getId()
                                + File.separator + str);
                        if(!file2.exists()) {
                            file2.mkdir();
                        }

                        File newFile = new File( FILE_ROOT_PATH + File.separator + user.getId()
                                + File.separator + str + File.separator  +newFileName);
                        // 先将文件进行传输到本地
                        file.transferTo(newFile);

                        // 判断是否为压缩文件
                        if(fileHandlerUtil.isZip(file.getOriginalFilename())){
                            //  将文件进行解压  解压的路径为基础//userid //日期//文件名 存储路径
                            //  开启新的线程进行解压操作
                            Thread thread = new Thread(() -> {
                                try {
                                    String sourcePath = newFile.getAbsolutePath();
                                    String desPaht = FILE_ROOT_PATH + user.getId()
                                            + File.separator + str;
                                    String cmd = env.getProperty("file.zipsoftPath");
                                    cmd = "\"" + cmd + "\"";
                                    String exec = cmd + " e " + sourcePath + " "+desPaht ;
                                    Process process = Runtime.getRuntime().exec(exec);
                                   //  等待解压命令执行完成
                                    process.waitFor();
                                    //执行完成及将用户上传的压缩文件进行删除删除
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

            // 保存上传文件的基础信息
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
            //  发送到消息队列中间件rabbitmq中
            rabbitTemplate.convertAndSend("file_handler_exchange",
                    "file.handler" , fileModle);
            //  上传结束
            return ResponseEntity.status(200).body("上传成功");
        }

        return ResponseEntity.status(500).body("文件上传错误！ 请重新上传");

    }



}
