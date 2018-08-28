package com.hudong.springboot;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages={"com.hudong"})
@EnableAutoConfiguration
@SpringBootApplication
@ServletComponentScan     //过滤器需要加上这个
@EnableTransactionManagement(order = 10) //开启事务，并设置order值，默认是Integer的最大值
public class SpringbootApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringbootApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
}
