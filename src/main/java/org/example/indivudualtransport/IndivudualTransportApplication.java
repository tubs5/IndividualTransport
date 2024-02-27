package org.example.indivudualtransport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IndivudualTransportApplication {

    public static String bingKey = "";
    public static void main(String[] args) {
        bingKey = args[0];
        SpringApplication.run(IndivudualTransportApplication.class, args);
    }

}
