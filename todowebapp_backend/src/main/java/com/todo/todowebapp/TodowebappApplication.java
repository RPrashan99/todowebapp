package com.todo.todowebapp;

import java.net.InetAddress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TodowebappApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TodowebappApplication.class);

		app.addListeners((ApplicationListener<ApplicationReadyEvent>) event -> {

			String port = "8080";

			try {
                String hostAddress = InetAddress.getLocalHost().getHostAddress();
                System.out.println("======================================");
                System.out.println("ToDoBackend Service started successfully!");
                System.out.println("Access URLs:");
                System.out.println("Local: http://localhost:" + port);
                System.out.println("External: http://" + hostAddress + ":" + port);
                System.out.println("======================================");
            } catch (Exception e) {
                e.printStackTrace();
            }

		});

		app.run(args);
		
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {

		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry corsRegistry){
				corsRegistry.addMapping("/api/**")
					.allowedOrigins(
						"http://localhost:3000"
					)
					.allowCredentials(true)
					.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
					.allowedHeaders("*");
			}
		};

	}

}
