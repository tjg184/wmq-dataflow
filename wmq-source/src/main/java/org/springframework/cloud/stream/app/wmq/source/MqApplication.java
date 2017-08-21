package org.springframework.cloud.stream.app.wmq.source;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MqApplication {
	public static void main(String[] args) {
		SpringApplication.run(MqApplication.class, args);
	}
}
