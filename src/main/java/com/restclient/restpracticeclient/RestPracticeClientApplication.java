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
        if (args.length == 0) {
            System.out.println("Ошибка: Отсутствуют параметры запуска программы, требуется перезапуск с параметрами");
            System.out.println("(Возможные параметры: -get_id / -get_all / -get_volt / " +
                    "-post_file / -post_cmd / -put_ft / -del_id)");
            return;
        }
        microchipService.selectMode(args[0]);
    }
}