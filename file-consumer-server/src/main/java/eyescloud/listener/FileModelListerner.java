package eyescloud.listener;


import com.eyescloud.entity.FileModle;
import com.eyescloud.service.FileHandlerService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class FileModelListerner {
    @Autowired
    private FileHandlerService fileHandlerService ;

    @Autowired
    private Environment env;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    // 监听队列
    @RabbitListener(queues = "file_handler_queue" , containerFactory = "fileListenerRabbitContainerFactory")
    public void fileListener(Message message ,
                             FileModle fileModle,
                             Channel channel)  {
        Thread thread = new Thread(){
            @Override
            public void run() {
                // 获取基础路径
                String basePath = env.getProperty("file.uploadFolder").replaceAll("file:","");
                StringBuilder stb = new StringBuilder();
                stb.append(basePath);
                stb.append(fileModle.getFilePath());
                String baseResultPath = env.getProperty("file.resultPath") ;
                String sourcePath = stb.toString();
                String resultPath = baseResultPath + fileModle.getId() + File.separator + format.format(new Date()) + File.separator;
                String pythonFilePath = env.getProperty("file.pythonFilePath");
                StringBuilder cmdBuilder = new StringBuilder();
                File resultDir = new File(resultPath);
                // 判断是否存在该路径  不存在则创建
                if(!resultDir.exists()){
                    resultDir.mkdirs();
                }
                // 生成cmd 命令
                cmdBuilder.append("python").append(" ").append(pythonFilePath).append(" ").append(sourcePath).append(" ")
                        .append(resultPath);
                //System.out.println(cmdBuilder.toString());
                try {
                    // 执行py命令
                    Process exec = Runtime.getRuntime().exec(cmdBuilder.toString());
                    exec.waitFor();
                    // 执行完成将文件状态改为1识别完成
                    fileModle.setStatus(1);

                    StringBuilder banZipCmd = new StringBuilder();
                    // 生成zip文件的文件名
                    String zipName = fileModle.getId()+File.separator
                            + format.format(new Date()) + File.separator
                            + UUID.randomUUID() + ".zip";
                    // 将要存储的路径
                    String resultZipPath = env.getProperty("file.zipPath") + zipName;

                    String banZipPath = env.getProperty("file.zipsoftPath");
                    banZipCmd.append(banZipPath).append(" ").append("a")
                            .append(" ").append(resultZipPath)
                            .append(" ").append(resultPath);
                    // 执行压缩命令
                    Runtime.getRuntime().exec(banZipCmd.toString());
                    // 将压缩的路径存储到数据库中
                    fileModle.setZipPath(zipName);
                    //  更新文件
                    fileHandlerService.updateFile(fileModle);

                    channel.basicAck(message.getMessageProperties().getDeliveryTag() , false);
                }catch (Exception e){
                    try {
                        channel.basicNack(message.getMessageProperties().getDeliveryTag() , true , true);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    log.error(e.toString());

                }

            }
        };
        log.info("接收到fileModle开始进行处理");
        thread.start();
        log.info(fileModle.toString());
    }

}
