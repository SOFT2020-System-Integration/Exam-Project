package app.controllers.rest;


import app.models.shipment.CamundaGame;
import app.repositories.ShippingCamundaClient;
import org.springframework.web.bind.annotation.*;


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
        return "Sent Shipment request to Camunda!";
    }
}