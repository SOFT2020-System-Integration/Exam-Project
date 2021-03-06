package app.repositories;

import app.config.RibbonConfig;
import app.models.customer.Customer;
import app.models.game.Game;
import app.models.order.Order;
import app.models.order.OrderLine;
import app.models.order.Status;
import app.models.shipment.CamundaGame;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@FeignClient("mongo-service")
@RibbonClient(name = "mongo-service", configuration = RibbonConfig.class)
public interface MongoClient {
    /* CUSTOMER */
    @GetMapping("/customers")
    Collection<Customer> customerCollection();
    @PostMapping("/customers/login/{mail}/{password}")
    Customer loginCustomer(@PathVariable String mail, @PathVariable String password);

    /* GAMES */
    @GetMapping("/games")
    Collection<Game> gameCollection();

    /* ORDERS */
    @GetMapping("/orders")
    Collection<Order> orderCollection();
    @PostMapping("/orders/create")
    Order orderCollectionPost(@RequestBody Order order);
    @DeleteMapping("/orders/delete/{id}")
    String orderCollectionDelete(@PathVariable String id);
    @PutMapping("/orders/id/{orderId}/orderlines/{orderlineId}/status/set/{status}")
    Order orderLineUpdateStatusPut(@PathVariable String orderId, @PathVariable String orderlineId, @PathVariable Status status);
    @PutMapping("/orders/id/{id}/status/set/{status}")
    Order updateOrderStatus(@PathVariable String id, @PathVariable Status status);
}