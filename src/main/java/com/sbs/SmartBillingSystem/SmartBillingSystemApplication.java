package com.sbs.SmartBillingSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @EntityScan(basePackages = "com.sbs.SmartBillingSystem.Entity")
public class SmartBillingSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(SmartBillingSystemApplication.class, args);
		System.out.print("started.....");

	}

}
