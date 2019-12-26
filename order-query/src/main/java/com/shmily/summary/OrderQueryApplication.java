package com.shmily.summary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
// 暂时不链接数据库
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class OrderQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderQueryApplication.class, args);
    }

}
