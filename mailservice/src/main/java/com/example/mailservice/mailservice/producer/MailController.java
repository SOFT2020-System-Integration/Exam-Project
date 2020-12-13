package com.example.mailservice.mailservice.producer;


import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@RestController
@RequestMapping("/kafka")

public class MailController {
    @Autowired
    private ProducerService service;

    @Autowired
    private MailService mailService;

    // Option 1:
    @PostMapping(value = "/message/{message}")
    public String sendMyMessageToKafka(@PathVariable("message") String message)
    {
        service.sendMessage(message);
        return "Message published: " + message;
    }

    @PostMapping(value = "/mail/all")
    public String sendAllEmails() throws SAXException, ParserConfigurationException, ParseException, IOException {
        mailService.createMails();
        return "Messages has been sent to the mails in our people.json file. This is pretty Smart isn't it";
    }
}
