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
    public String createProcess2(@RequestBody Game game) {
        try {
            CloseableHttpResponse call = makeCamundaCreateProcessRequestGame(game);
            if(call != null) {
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
}
