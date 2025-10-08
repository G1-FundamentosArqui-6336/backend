package org.upc.cobox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CoboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoboxApplication.class, args);
    }

}
