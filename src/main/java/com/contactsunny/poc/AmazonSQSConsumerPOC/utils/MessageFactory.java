package com.contactsunny.poc.AmazonSQSConsumerPOC.utils;

import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageFactory {

    @Value("${sqs.url}")
    private String sqsUrl;

    public SendMessageRequest build() {
        return new SendMessageRequest().withQueueUrl(sqsUrl);
    }


}
