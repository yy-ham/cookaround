package com.project.cookaround;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.project.cookaround.domain")
public class CookaroundApplication {

	public static void main(String[] args) {
		SpringApplication.run(CookaroundApplication.class, args);
	}

}
