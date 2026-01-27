package iteam.tn.gestionformation;
import org.springframework.cloud.openfeign.EnableFeignClients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableFeignClients(basePackages = "iteam.tn.gestionformation")
@SpringBootApplication
public class GestionFormationApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionFormationApplication.class, args);
	}

}
