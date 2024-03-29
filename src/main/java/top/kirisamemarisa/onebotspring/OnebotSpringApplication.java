package top.kirisamemarisa.onebotspring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("top.kirisamemarisa.onebotspring.mapper")
public class OnebotSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnebotSpringApplication.class, args);
    }

}
