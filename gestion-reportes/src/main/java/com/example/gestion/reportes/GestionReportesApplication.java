package com.example.gestion.reportes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GestionReportesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionReportesApplication.class, args);
	}

	    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
