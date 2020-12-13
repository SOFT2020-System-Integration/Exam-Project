package com.example.mailservice.mailservice.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProducerService {
    private static final String TOPIC = "message-topic";
    private static Logger logger = LoggerFactory.getLogger(ProducerService.class);

    @Autowired
    // Ignore the compiler's warning
    private KafkaTemplate<String, String> template;

    public void sendMessage(String message)
    {
        template.send(TOPIC, message);
        // logger.info(String.format("### -> Producer sends message -> %s", message));
        logger.info("### Producer sends message [{}]", message);
        template.flush();
    }
}
