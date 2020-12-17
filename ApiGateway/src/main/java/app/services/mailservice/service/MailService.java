package app.services.mailservice.service;

import app.repositories.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class MailService {
    private final List<String> messages = new ArrayList<>();


    private final MongoClient client;
    public MailService(MongoClient client){
        this.client = client;
    }
    // get logger for my class
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);


    @KafkaListener(topics = "order-broker", groupId = "my-group")
    public void listenToMessages(String message) throws IOException {
        synchronized (messages) {
            messages.add(message);
            client.
        }

        logger.info("&&& Message Consumed: [" + message + "]");
    }

    public List<String> getMessages() {
        return messages;
    }
}
