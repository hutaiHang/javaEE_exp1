package com.cqu.hth.ssoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.cqu.hth.ssoapp")
public class SsoappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsoappApplication.class, args);
	}

}
