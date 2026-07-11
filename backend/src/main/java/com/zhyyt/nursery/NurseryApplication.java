package com.zhyyt.nursery;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.zhyyt.nursery.module.**.mapper")
@EnableAsync
@EnableScheduling
public class NurseryApplication {
    public static void main(String[] args) {
        SpringApplication.run(NurseryApplication.class, args);
        System.out.println("""

                ╔══════════════════════════════════════════╗
                ║                                          ║
                ║   智慧托育综合应用平台 启动成功!           ║
                ║                                          ║
                ║   API文档: http://localhost:8080/doc      ║
                ║                                          ║
                ╚══════════════════════════════════════════╝
                """);
    }
}
