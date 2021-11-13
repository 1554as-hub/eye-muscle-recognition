package eyescloud.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMqConfig {



    @Bean
    public SimpleRabbitListenerContainerFactory fileListenerRabbitContainerFactory(ConnectionFactory factory){
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();

        containerFactory.setConnectionFactory(factory);
        // 设置手动确认
        containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //  设置最大的消费数
        containerFactory.setMaxConcurrentConsumers(5);
        //  每次给消费者发送的数量
        containerFactory.setPrefetchCount(1);

        return containerFactory;
    }

}
