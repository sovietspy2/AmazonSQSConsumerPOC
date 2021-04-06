package com.contactsunny.poc.AmazonSQSConsumerPOC;

import com.contactsunny.poc.AmazonSQSConsumerPOC.utils.SQSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private SQSUtil sqsUtil;

    @GetMapping(path="/get")
    public String get() {
        sqsUtil.get();
        return "SUCCESS";
    }

    @GetMapping(path = "/send/{message}")
    private String send(@PathVariable String message) {
        for (int i = 0; i<10; i++) {
            sqsUtil.send(message+i);
        }
        return "ok";
    }

}
