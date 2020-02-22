package com.product.registration.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;

@SpringBootApplication
public class UserRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserRegistrationApplication.class, args);
	}

}


@Configuration
class RestTemplateConfig {
	
	
 // Create a bean for restTemplate to call services

     @Bean
	//@LoadBalanced // Load balance between service instances running at different  ports. 
    public RestTemplate restTemplate() { return new RestTemplate(); }

	
	/*
	 * @Primary
	 * 
	 * @Bean RestTemplate restTemplate() { return new RestTemplate(); }
	 * 
	 * @LoadBalanced
	 * 
	 * @Bean RestTemplate loadBalanced() { return new RestTemplate(); }
	 */
	
}
