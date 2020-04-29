package cn.kebabshell.xiafan_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("cn.kebabshell.xiafan_demo.common.mapper")
@EntityScan("cn.kebabshell.xiafan_demo.common.pojo")
public class XiafanDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiafanDemoApplication.class, args);
    }

}
