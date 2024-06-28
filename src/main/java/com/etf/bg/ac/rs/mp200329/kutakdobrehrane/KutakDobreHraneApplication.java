package com.etf.bg.ac.rs.mp200329.kutakdobrehrane;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class KutakDobreHraneApplication {

	public static void main(String[] args) {
		SpringApplication.run(KutakDobreHraneApplication.class, args);
	}

}
