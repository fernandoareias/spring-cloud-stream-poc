package com.fernando.spring_cloud_stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// <function-bean-name>-<in>|<out>-[0..n],
@SpringBootApplication
public class SpringCloudStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamApplication.class, args);
	}
}
