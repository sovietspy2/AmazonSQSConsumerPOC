package com.contactsunny.poc.AmazonSQSConsumerPOC;

import com.contactsunny.poc.AmazonSQSConsumerPOC.utils.SQSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
