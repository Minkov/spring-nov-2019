package com.minkov.springbootintro.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    private static ModelMapper mapper;

    static {
        mapper = new ModelMapper();
        // config with mappings
    }

    @Bean
    public ModelMapper modelMapper() {
        return mapper;
    }
}
