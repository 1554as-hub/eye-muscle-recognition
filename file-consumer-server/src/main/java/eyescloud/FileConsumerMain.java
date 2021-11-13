package eyescloud;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.eyescloud.mapper")
public class FileConsumerMain {

    public static void main(String[] args) {
        SpringApplication.run(FileConsumerMain.class , args);
    }

}
