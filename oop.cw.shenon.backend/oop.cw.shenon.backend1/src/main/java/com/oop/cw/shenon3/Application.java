package com.oop.cw.shenon3;

import com.oop.cw.shenon3.Config.LoggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Initializes the spring boot aplication and runs
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		LoggerConfig.configurelogger();
		SpringApplication.run(Application.class, args);
	}

}
