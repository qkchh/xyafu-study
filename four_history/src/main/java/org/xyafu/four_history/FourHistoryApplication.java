package org.xyafu.four_history;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@MapperScan("org.xyafu.four_history.mapper")
@CrossOrigin
public class FourHistoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(FourHistoryApplication.class, args);
    }

}
