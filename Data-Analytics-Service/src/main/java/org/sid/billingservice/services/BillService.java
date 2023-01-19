package org.sid.billingservice.services;

import org.sid.billingservice.entities.Bill;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

public class BillService {
    @Bean
    public Consumer<Bill> pageBillConsumer(){
        return  (input)->{
            System.out.println("****************************");
            System.out.println(input.toString());
            System.out.println("****************************");
        };
    }
}
