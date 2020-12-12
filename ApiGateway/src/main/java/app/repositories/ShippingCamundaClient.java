package app.repositories;

import app.config.RibbonConfig;
import app.models.game.Game;
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
    /*
    @GetMapping("/shipping")
    String greetApi();
    */

    @PostMapping("/shipping/create-process")
    String camundaPost(@RequestBody CamundaGame shipment);
}