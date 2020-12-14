package app.shipping.controllers;


import app.shipping.models.CamundaDataDefiner;
import app.shipping.models.CamundaDataObject;
import app.shipping.models.CamundaDataVariables;
import app.shipping.models.game.Game;
import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;



import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileReader;
import java.io.IOException;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;


//@RepositoryRestController

@RepositoryRestResource
@ResponseBody
@RestController
@RequestMapping("/shipping")


public class ShippingCamundaController {

    @Autowired



    private static final String TOPIC = "email-broker";


    private static final Logger LOGGER = Logger.getLogger(String.valueOf(ShippingCamundaController.class));

    @GetMapping("/")
    public String get() {
        return "Shipping Api!";
    }

    @GetMapping("/yeet")
    public String yeet() {
        return "Shipping yeet!";
    }

    @PostMapping("/test")
    public String test(@RequestBody String entity) {
        LOGGER.log(Level.INFO, "[LOGGER] ::: TEST ::: STRING! " + entity);
        return entity;
    }

    @PostMapping("/create-process")
    public String createProcess2(@RequestBody Game game) {

        try {
            CloseableHttpResponse call = makeCamundaCreateProcessRequestGame(game);
            if (call != null) {
                createMails(game);
                LOGGER.log(Level.INFO, "[LOGGER] ::: SHIPMENT ::: createProcess");
                return "Successfully sent object to Camunda.";
            }
            return "Something went wrong.";
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "[LOGGER] ::: Error occur in createProcess ::: ", e.getMessage());
            return "Error, something went wrong, check the console.";
        }
    }

    public CloseableHttpResponse makeCamundaCreateProcessRequestGame(Game game) {
        CloseableHttpResponse response;
        CamundaDataDefiner camundDataGame;
        CamundaDataDefiner camundDataShipType;
        CamundaDataVariables camundVars;
        CamundaDataObject camundObj;
        try {
            camundDataGame = new CamundaDataDefiner(game.getId(), "String");
            camundDataShipType = new CamundaDataDefiner(game.getType().getType().toString(), "String");
            camundVars = new CamundaDataVariables(camundDataGame, camundDataShipType);
            camundObj = new CamundaDataObject(camundVars);

            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:8080/engine-rest/process-definition/key/GameShipment/start");
            Gson gson = new Gson();
            String json = gson.toJson(camundObj);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            response = client.execute(httpPost);
            response.close();
            return response;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "[LOGGER] ::: Error occur in createProcess ::: ", e.getMessage());
            return null;
        }
    }

    public void createMails(Game game) throws SAXException, ParserConfigurationException, IOException, ParseException {
        String message = getEmailTemplate();
        message = message.replace("{title}", game.getTitle());
        message = message.replace("{id}", game.getId());

        sendMessage(message);

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

    @Autowired
    // Ignore the compiler's warning
    private KafkaTemplate<String, String> template;

    public void sendMessage(String message) {
        template.send(TOPIC, message);
        // logger.info(String.format("### -> Producer sends message -> %s", message));
        LOGGER.log(Level.INFO,"### Producer sends message [{}]",message);
        template.flush();
    }

}
