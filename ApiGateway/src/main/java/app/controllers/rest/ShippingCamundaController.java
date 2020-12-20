package app.controllers.rest;


import app.models.order.Order;
import app.models.shipment.CamundaGame;
import app.repositories.ShippingCamundaClient;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/shipping")
public class ShippingCamundaController {
    private final ShippingCamundaClient client;
    public ShippingCamundaController(ShippingCamundaClient client) {
        this.client = client;
    }

    @PostMapping("/create-shipment")
    @CrossOrigin(origins = "*") // allow request from any client
    public String createShipment(@RequestBody Order shipment)
    {
        client.camundaPost(shipment);
        return "Sent Shipment request to Camunda!";
    }
}