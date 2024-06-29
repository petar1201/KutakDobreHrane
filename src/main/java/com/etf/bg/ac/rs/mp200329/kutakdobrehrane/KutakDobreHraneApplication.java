package com.etf.bg.ac.rs.mp200329.kutakdobrehrane;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@CrossOrigin
public class KutakDobreHraneApplication {

	public static void main(String[] args) {
		SpringApplication.run(KutakDobreHraneApplication.class, args);
	}

}
