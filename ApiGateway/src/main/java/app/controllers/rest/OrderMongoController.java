package app.controllers.rest;

import app.exceptions.NotFoundException;
import app.helpers.Encrypt;
import app.models.customer.Customer;
import app.models.order.Order;
import app.models.order.OrderLine;
import app.models.order.Status;
import app.repositories.MongoClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/orders")
public class OrderMongoController {
    private final MongoClient client;
    public OrderMongoController(MongoClient client) {
        this.client = client;
    }

    @GetMapping("")
    @CrossOrigin(origins = "*") // allow request from any client
    public Collection<Order> retrieveAllOrders() throws NotFoundException
    {
        List<Order> collect = client.orderCollection()
                .stream()
                .collect(Collectors.toList());
        if(!collect.isEmpty()) {
            return collect;
        } else {
            throw new NotFoundException(String.format("Customers not found..."));
        }
    }

    @GetMapping("/id/{id}")
    @CrossOrigin(origins = "*") // allow request from any client
    public Order retrieveOrderById(@PathVariable String id) throws NotFoundException
    {
        List<Order> collect = client.orderCollection()
                .stream()
                .filter(Order -> Order.getId().equals(id))
                .collect(Collectors.toList());
        if(!collect.isEmpty()) {
            return collect.get(0);
        } else {
            throw new NotFoundException(String.format("Customer with id '%s' not found...", id));
        }
    }

    @GetMapping("/id/{id}/orderlines")
    @CrossOrigin(origins = "*") // allow request from any client
    public List<OrderLine> retrieveOrderLinesByOrderId(@PathVariable String id) throws NotFoundException
    {
        List<Order> collect = client.orderCollection()
                .stream()
                .filter(Order -> Order.getId().equals(id))
                .collect(Collectors.toList());
        if(!collect.isEmpty()) {
            return collect.get(0).getOrderLines();
        } else {
            throw new NotFoundException(String.format("Couldn't find any orderlines for order with id: %s", id));
        }
    }

    @PostMapping("/create")
    @CrossOrigin(origins = "*") // allow request from any client
    public Order createOrder(@RequestBody Order order) {
        return client.orderCollectionPost(order);
    }

    @DeleteMapping("/delete/{id}")
    @CrossOrigin(origins = "*") // allow request from any client
    public String deleteOrder(@PathVariable String id) {
        try {
            client.orderCollectionDelete(id);
            return "Deleted Order of id: " + id;
        } catch (Exception ex) {
            throw new NotFoundException("Order not found...");
        }
    }

    @PutMapping("/id/{id}/status/set/{status}")
    public Order updateOrderStatus(@PathVariable String id, @PathVariable Status status) throws RuntimeException {
        Optional<Order> _orderOpt;
        try {
            _orderOpt = Optional.of(retrieveOrderById(id));
            if(status.equals(Status.IN_PROGRESS) || status.equals(Status.COMPLETED)) {
                _orderOpt.get().setStatus(status);
                client.updateOrderStatus(id, status);
                return _orderOpt.get();
            } else {
                throw new RuntimeException("Something went wrong.");
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
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
