package com.nagne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NagneApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(NagneApplication.class, args);
  }
  
}
