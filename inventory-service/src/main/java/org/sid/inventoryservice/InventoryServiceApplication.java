package org.sid.inventoryservice;

import org.sid.inventoryservice.enteties.Product;
import org.sid.inventoryservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(ProductRepository productRepository,
							RepositoryRestConfiguration restConfiguration){
		restConfiguration.exposeIdsFor(Product.class);
		return  args -> {
				productRepository.save(new Product(1L, "product1" , 10, 5));
				productRepository.save(new Product(2L, "product2" , 20, 3));
				productRepository.save(new Product(3L, "product3" , 5, 2));
				productRepository.findAll().forEach(p->{
					System.out.println(p.getName());
				});
		};
	}
}
