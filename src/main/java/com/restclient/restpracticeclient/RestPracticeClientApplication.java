package com.restclient.restpracticeclient;

import com.restclient.restpracticeclient.service.MicrochipService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@EnableFeignClients
@SpringBootApplication
public class RestPracticeClientApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RestPracticeClientApplication.class, args);
        MicrochipService microchipService = context.getBean(MicrochipService.class);
        microchipService.showMenu();
    }
}