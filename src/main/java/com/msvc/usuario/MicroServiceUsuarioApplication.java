package com.msvc.usuario;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;



@SpringBootApplication
@EnableDiscoveryClient
public class MicroServiceUsuarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceUsuarioApplication.class, args);
	}

}
