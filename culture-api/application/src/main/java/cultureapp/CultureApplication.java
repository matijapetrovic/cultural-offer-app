package cultureapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CultureApplication {
    public static void main(String[] args) {
        SpringApplication.run(CultureApplication.class, args);
    }
}
