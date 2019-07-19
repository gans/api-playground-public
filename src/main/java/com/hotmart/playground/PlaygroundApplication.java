package com.hotmart.playground;

import com.hotmart.playground.conf.ApiProperties;
import com.hotmart.playground.storage.StorageProperties;
import com.hotmart.playground.storage.StorageService;
import org.apache.tika.Tika;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({ApiProperties.class, StorageProperties.class})
public class PlaygroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlaygroundApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Tika tika() {
        return new Tika();
    }

    @Bean
    public CommandLineRunner init(StorageService storageService) {
        return args -> storageService.init();
    }
}
