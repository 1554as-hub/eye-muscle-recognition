package eyescloud.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan( basePackages = {"com.eyescloud.service.*" , "com.eyescloud.config"})
public class ComponentConfig {
}
