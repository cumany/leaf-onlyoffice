package com.ideayp.leaf.onlyoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 95765
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableScheduling
public class OnlyOfficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlyOfficeApplication.class, args);
    }
}
