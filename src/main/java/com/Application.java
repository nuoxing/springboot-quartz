package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

/**
 * 项目启动类
 * @author ljj
 * @SpringBootApplication springboot应用注解
 *
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class Application extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
    
    
    /**
     * 将项目达成war包的配置
     * 进入项目的src目录下面 运行mvn package 生成war包
     * 或者在eclipse run as maven build  
     * 输入命令 clean package 进行打包
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // TODO Auto-generated method stub
        // return super.configure(builder);
        return builder.sources(Application.class);
    }
}

