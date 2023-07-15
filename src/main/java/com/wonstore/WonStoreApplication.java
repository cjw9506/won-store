package com.wonstore;

import com.wonstore.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class WonStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(WonStoreApplication.class, args);
	}

}
