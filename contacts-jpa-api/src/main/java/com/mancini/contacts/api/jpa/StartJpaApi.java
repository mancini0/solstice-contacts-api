package com.mancini.contacts.api.jpa;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


@SpringBootApplication
@EnableSpringDataWebSupport
@EntityScan("com.mancini.contacts.domain.jpa")
public class StartJpaApi {

    public static void main(String[] args){
        SpringApplication.run(StartJpaApi.class);
    }

}
