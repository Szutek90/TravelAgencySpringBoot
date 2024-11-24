package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TravelAgencySpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelAgencySpringBootApplication.class, args);
    }

    //TODO [ 1 ] Dlaczego mimo, że w CountryEntity mam tylko pola id, name to w DB stworzyło mi dodatkowe kolumny agency_id,
    // customer_id, discount, quantity of people. Te kolumny powinny być w

}
