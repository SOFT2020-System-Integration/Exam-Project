package app.services.mailservice.controller;

import app.services.mailservice.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/kafka")
public class MailController {

    @Autowired
    private MailService service;

    // Option 1:
    @GetMapping("/all")
    public List<String> sendMyMessageToKafka()
    {
        return service.getMessages();

    }

}