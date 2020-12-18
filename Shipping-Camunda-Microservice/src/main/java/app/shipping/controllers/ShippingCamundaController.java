package app.shipping.controllers;


import app.shipping.models.CamundaDataDefiner;
import app.shipping.models.CamundaDataObject;
import app.shipping.models.CamundaDataVariables;
import app.shipping.models.orderline.Order;
import app.shipping.models.orderline.OrderLine;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;



import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
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


    private static final String TOPIC = "order-broker";
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(ShippingCamundaController.class));

    @GetMapping("/")
    public String get() {
        return "Shipping Api!";
    }

    @PostMapping("/create-shipment")
    public String createShipment(@RequestBody Order order) {
        for(OrderLine orderline: order.getOrderLines())
        {
            orderline.setOrderId(order.getId());
            try {
                CloseableHttpResponse call = makeCamundaCreateProcessRequestForOrder(orderline, order.getId());
                if (call != null) {
                    LOGGER.log(Level.INFO, "[LOGGER] ::: SHIPMENT ::: createShipment");
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "[LOGGER] ::: Error occur in createProcess ::: ", e.getMessage());
                return "Error, something went wrong, check the console.";
            }
        }
        return String.format("Successfully sent orderlines to Camunda", order.getOrderLines());
    }

    public CloseableHttpResponse makeCamundaCreateProcessRequestForOrder(OrderLine orderline, String orderId) {
        CloseableHttpResponse response;
        CamundaDataDefiner camundDataGame;
        CamundaDataDefiner camundDataShipType;
        CamundaDataVariables camundVars;
        CamundaDataObject camundObj;
        CamundaDataDefiner camundaDataStatus;
        CamundaDataDefiner camundaDataOrderLineId;
        CamundaDataDefiner camundaDataOrderId;
        try {
            camundDataGame = new CamundaDataDefiner(orderline.getId(), "String");
            camundDataShipType = new CamundaDataDefiner(orderline.getGame().getType().getType().toString(), "String");
            camundaDataStatus = new CamundaDataDefiner(orderline.getStatus().toString(),"String");
            camundaDataOrderLineId = new CamundaDataDefiner(orderline.getId(),"String");
            camundaDataOrderId = new CamundaDataDefiner(orderId,"String");
            camundVars = new CamundaDataVariables(camundDataGame, camundDataShipType,camundaDataStatus,camundaDataOrderLineId, camundaDataOrderId);
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

    public void shipmentConfirmedSent(String orderId, String orderlineId) {
        JsonObject json = new JsonObject();
        json.addProperty("orderId", orderId);
        json.addProperty("orderlineId", orderlineId);
        sendMessage(json.toString());
    }

    public void createMails(OrderLine orderLine) throws SAXException, ParserConfigurationException, IOException, ParseException {
        //String message = getEmailTemplate();
        //message = message.replace("{title}", orderLine.getGame().getTitle());
        //message = message.replace("{id}", orderLine.getId());

        String message = orderLine.toString();
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
    //Ignore this warning
    private KafkaTemplate<String, String> template;

    public void sendMessage(String message) {
        template.send(TOPIC, message);
        // logger.info(String.format("### -> Producer sends message -> %s", message));
        LOGGER.log(Level.INFO,"### Producer sends message: " + message);
        template.flush();
    }

}
