package app.controllers.rest;

import app.models.game.Game;
import app.models.order.Order;
import app.models.order.OrderLine;
import app.models.order.Status;
import app.models.shipment.CamundaGame;
import app.repositories.MongoClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/orders")
public class OrderMongoController {
    MongoClient client;

    public OrderMongoController(MongoClient client) {
        this.client = client;
    }

    @GetMapping("")
    @CrossOrigin(origins = "*") // allow request from any client
    public Collection<Order> myGame() {
        List<Order> collect = client.orderCollection()
                .stream()
                .collect(Collectors.toList());
        return collect;
    }


    @GetMapping("/id/{id}")
    @CrossOrigin(origins = "*") // allow request from any client
    public Order retrieveOrderById(@PathVariable String id) {
        List<Order> collect = client.orderCollection()
                .stream()
                .filter(Order -> Order.getId().equals(id))
                .collect(Collectors.toList());
        return collect.get(0);
    }

    @GetMapping("/id/{id}/orderlines")
    @CrossOrigin(origins = "*") // allow request from any client
    public List<OrderLine> retrieveOrderLinesByOrderId(@PathVariable String id) {
        List<Order> collect = client.orderCollection()
                .stream()
                .filter(Order -> Order.getId().equals(id))
                .collect(Collectors.toList());
        return collect.get(0).getOrderLines();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @CrossOrigin(origins = "*") // allow request from any client
    public String orderPost(@RequestBody Order order) {
        client.orderPost(order);
        return "Created order with id: " + order.getId();
    }

    @DeleteMapping("/delete/{id}")
    @CrossOrigin(origins = "*") // allow request from any client
    public String orderDelete(@PathVariable String id) {
        client.orderDelete(id);
        return "Deleted record of " + id;
    }

    @PutMapping("/orders/test/{test}")
    @CrossOrigin(origins = "*") // allow request from any client
    public String putTest(@PathVariable String test) {
        return client.orderPutTest(test);
    }

    @PutMapping("/id/{orderId}/orderlines/{orderlineId}/status/set/{status}")
    @CrossOrigin(origins = "*")
    public Order updateOrderLineStatusByOrderAndOrderLineId(@PathVariable String orderId, @PathVariable String orderlineId, @PathVariable Status status) {
        Optional<Order> _orderOpt;
        OrderLine orderLineToUpdate;

        try {
            _orderOpt = Optional.of(retrieveOrderById(orderId));
            System.out.println(_orderOpt);
            if(status.equals(Status.IN_PROGRESS) || status.equals(Status.COMPLETED)) {
                List<OrderLine> list = _orderOpt.get().getOrderLines().stream()
                        .filter(orderLine -> orderLine.getId().equals(orderlineId))
                        .collect(Collectors.toList());
                System.out.println(list);
                orderLineToUpdate = list.get(0);
                orderLineToUpdate.setStatus(status);
                client.orderLineUpdateStatusPut(orderId, orderlineId, status);
                return _orderOpt.get();
            } else {
                throw new RuntimeException("Something went wrong.");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
