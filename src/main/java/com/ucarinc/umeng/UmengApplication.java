//修改代码之后重新run 才能生效

package com.ucarinc.umeng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com.*"})
@MapperScan({"com.ucarinc.umeng.dao"})
public class UmengApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmengApplication.class, args);
    }

}
