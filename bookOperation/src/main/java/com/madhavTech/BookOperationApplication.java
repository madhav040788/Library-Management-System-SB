package com.madhavTech;

import com.madhavTech.dto.BookResponse;
import com.madhavTech.service.BookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.List;

@SpringBootApplication
@EnableCaching
public class BookOperationApplication {


	public static void main(String[] args) {
		SpringApplication.run(BookOperationApplication.class, args);
	}

}
