package com.coconutcode.salesbatchservice;

import com.coconutcode.salesbatchservice.service.ViewService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SalesBatchServiceApplication implements CommandLineRunner {

	@Autowired
	private ViewService viewService;

	public static void main(String[] args) {
		SpringApplication.run(SalesBatchServiceApplication.class, args);
	}

	@Override
	public void run(String... args) {
		viewService.generate();
	}
}
