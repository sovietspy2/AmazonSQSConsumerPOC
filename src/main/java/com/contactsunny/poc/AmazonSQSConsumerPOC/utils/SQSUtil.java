package com.contactsunny.poc.AmazonSQSConsumerPOC.utils;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class SQSUtil {

    @Value("${sqs.url}")
    private String sqsUrl;

    @Value("${aws.accessKey}")
    private String awsAccessKey;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

    @Value("${aws.region}")
    private String awsRegion;

    private AmazonSQS amazonSQS;

    private static final Logger logger = LoggerFactory.getLogger(SQSUtil.class);

    @Autowired
    private MessageFactory messageFactory;

    @PostConstruct
    private void postConstructor() {

        logger.info("SQS URL: " + sqsUrl);

        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(awsAccessKey, awsSecretKey)
        );

        this.amazonSQS = AmazonSQSClientBuilder.standard().withCredentials(awsCredentialsProvider).withRegion(awsRegion).build();
    }

    public void send(String message) {

        amazonSQS.sendMessage(messageFactory.build().withMessageBody(message));

    }

    public void get() {

        logger.info("soon to get messages");

        final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(sqsUrl)
                .withMaxNumberOfMessages(5)
                .withWaitTimeSeconds(10);

        logger.info(" fetching messages");

            final List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();

            for (Message messageObject : messages) {
                String message = messageObject.getBody();

                logger.info("Received message: " + message);

                deleteMessage(messageObject);
            }

        logger.info(" done fetching messages");
    }

    private void deleteMessage(Message messageObject) {

        final String messageReceiptHandle = messageObject.getReceiptHandle();
        amazonSQS.deleteMessage(new DeleteMessageRequest(sqsUrl, messageReceiptHandle));

    }
}
