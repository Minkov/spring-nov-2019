package com.minkov.springbootintro;

import com.minkov.springbootintro.services.services.BeersService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootintroApplication {
    public static void main(String[] args) {
       ConfigurableApplicationContext ctx = SpringApplication.run(SpringbootintroApplication.class, args);
       BeersService beersService = ctx.getBean(BeersService.class);
    }
}
