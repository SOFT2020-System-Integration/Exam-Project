package app.repositories;

import app.config.RibbonConfig;
import app.models.game.Game;
import app.models.order.Order;
import app.models.shipment.CamundaGame;
import org.apache.coyote.Response;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@FeignClient("shipping-camunda")
@RibbonClient(name = "shipping-camunda", configuration = RibbonConfig.class)
public interface ShippingCamundaClient {
    @PostMapping("/shipping/create-shipment")
    String camundaPost(@RequestBody Order shipment);
}