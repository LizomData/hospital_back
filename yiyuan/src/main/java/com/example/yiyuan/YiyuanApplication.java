package com.example.yiyuan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.TimeZone;

@EnableAsync
@EnableTransactionManagement
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.example.yiyuan"})
@MapperScan(basePackages = {"com.example.yiyuan.mappers"})
public class YiyuanApplication {
    public static void main(String[] args) {
        SpringApplication.run(YiyuanApplication.class, args);
    }

}
