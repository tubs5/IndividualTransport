package org.example.indivudualtransport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(Config.class)
@SpringBootApplication
public class IndivudualTransportApplication {

    public static String bingKey = "AvzGhJqy4wJgyFwVMSJ9Iwq9NwOk6y4gBOrbjfNoBvLh_gQyHeG719jg3x1QjWq9";
    public static void main(String[] args) {
        SpringApplication.run(IndivudualTransportApplication.class, args);
    }

}
