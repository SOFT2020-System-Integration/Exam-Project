package app.controllers;


import app.models.game.Game;
import app.models.shipment.CamundaGame;
import app.repositories.GameCatalogClient;
import app.repositories.ShippingCamundaClient;
import org.apache.coyote.Response;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/shipping")
public class ShippingCamundaController {
    ShippingCamundaClient client;

    public ShippingCamundaController(ShippingCamundaClient client) {
        this.client = client;
    }

    @RequestMapping(value = "/create-process", method = RequestMethod.POST)
    @CrossOrigin(origins = "*") // allow request from any client
    public String camundaShipment(@RequestBody CamundaGame shipment)
    {
        client.camundaPost(shipment);
        return "Sent shipment!";
    }

    /*
    @GetMapping("")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client
    public String greetUser()
    {
        return client.greetApi();
    }
     */
}