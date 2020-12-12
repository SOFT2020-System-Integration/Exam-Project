package app.shipping.controllers;

import app.shipping.models.CamundaGame;
import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

//@RepositoryRestController
@RepositoryRestResource
@ResponseBody
@RestController
@RequestMapping("/shipping")
public class ShippingCamundaController {

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
    public String createProcess(@RequestBody CamundaGame camundaGame) {
        try {
            CloseableHttpResponse call = makeCamundaCreateProcessRequest(camundaGame);
            LOGGER.log(Level.INFO, "[LOGGER] ::: SHIPMENT ::: createProcess");
            return "Successfully sent object to Camunda.";
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "[LOGGER] ::: Error occur in createProcess ::: ", e.getMessage());
            return "Error, something went wrong, check the console.";
        }
    }


    public CloseableHttpResponse makeCamundaCreateProcessRequest(CamundaGame camundaGame) {
        CloseableHttpResponse response;
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:8080/engine-rest/process-definition/key/GameShipment/start");
            Gson gson = new Gson();
            String json = gson.toJson(camundaGame);
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
}
