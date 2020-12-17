package app.repositories;

import app.config.RibbonConfig;
import app.models.game.Game;
import app.models.order.Order;
import app.models.order.Status;
import app.models.shipment.CamundaGame;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@CrossOrigin
@FeignClient("mongo-service")
@RibbonClient(name = "mongo-service", configuration = RibbonConfig.class)
public interface MongoClient {
    /* GAMES */
    @GetMapping("/games")
    Collection<Game> gameCollection();

    /* ORDERS */
    @GetMapping("/orders")
    Collection<Order> orderCollection();

    @PostMapping("/orders/create")
    String orderPost(@RequestBody Order order);

    @PutMapping("/orders/test/{test}")
    String orderPutTest(@PathVariable String test);

    @PutMapping("/orders/id/{orderId}/orderlines/{orderlineId}/status/set/{status}")
    Order orderLineUpdateStatusPut(@PathVariable String orderId, @PathVariable String orderlineId, @PathVariable Status status);

    @DeleteMapping("/orders/delete/{id}")
    String orderDelete(@PathVariable String id);

    /* CUSTOMER */
}