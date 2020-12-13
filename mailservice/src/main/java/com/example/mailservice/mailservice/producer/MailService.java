package com.example.mailservice.mailservice.producer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Iterator;

import java.util.stream.Stream;


@Service
public class MailService {
    //@Autowired
    //private KafkaTemplate<String, String> template;

    @Autowired
    private ProducerService ps = new ProducerService();

    public void createMails() throws SAXException, ParserConfigurationException, IOException, ParseException {
        FileReader reader = new FileReader(System.getProperty("user.dir") + "/src/main/resources/people.json");
        JSONParser jsonParser = new JSONParser();
        JSONObject JO = (JSONObject) jsonParser.parse(reader);
        JSONArray JOs = (JSONArray) JO.get("Customers");
        Iterator<JSONObject> iterator = JOs.iterator();
        while (iterator.hasNext()) {
            JSONObject temp = iterator.next();
            String message = getEmailTemplate();
            message = message.replace("{name}", temp.get("name").toString());
            message = message.replace("{title}", getGenderTitle(temp.get("name").toString()));

            ps.sendMessage(message);
        }
    }


    public String getGenderTitle(String name) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL("http://www.thomas-bayer.com/restnames/name.groovy?name=" + name).openStream());
        NodeList NL = doc.getElementsByTagName("gender");
        if (NL.item(0).getTextContent().contains("unisex")) {
            return "Mr/Ms";
        } else if (NL.item(0).getTextContent().contains("female")) {
            return "Ms";
        } else if (NL.item(0).getTextContent().contains("male")) {
            return "Mr";
        } else {
            return "We don't know";
        }
    }

    public String findNameFromEmail(String email) throws IOException, ParseException {
        FileReader reader = new FileReader(System.getProperty("user.dir") + "/src/main/resources/people.json");
        JSONParser jsonParser = new JSONParser();
        JSONObject JO = (JSONObject) jsonParser.parse(reader);
        JSONArray JOs = (JSONArray) JO.get("Customers");
        Iterator<JSONObject> iterator = JOs.iterator();
        while (iterator.hasNext()) {
            JSONObject o = iterator.next();
            String mail = o.get("mail").toString();
            if (mail.equals(email)) {
                return o.get("name").toString();
            }
        }
        return null;
    }


    public String getEmailTemplate() throws IOException {
        StringBuilder content = new StringBuilder();
        try {
            Stream<String> stream = Files.lines(Path.of(System.getProperty("user.dir") + "/src/main/resources/TemplateEmail.txt"));
            stream.forEach(x -> content.append(x));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return content.toString();
    }
}
