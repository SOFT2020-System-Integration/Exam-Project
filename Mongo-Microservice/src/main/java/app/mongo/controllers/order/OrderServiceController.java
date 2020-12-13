package app.mongo.controllers.order;

import app.mongo.exceptions.NotFoundException;
import app.mongo.models.order.Order;
import app.mongo.models.order.OrderLine;
import app.mongo.models.order.Status;
import app.mongo.repositories.order.OrderLineService;
import app.mongo.repositories.order.OrderService;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@RepositoryRestController
@RepositoryRestResource
@ResponseBody
@RestController
@RequestMapping("/orders")
public class OrderServiceController
{
    @Autowired
    private OrderService orderRepo;
    private OrderLineService orderLineRepo;

    @GetMapping("/")
    public List<Order> retrieveAll() {
        return orderRepo.findAll();
    }

    @GetMapping("/id/{id}")
    public Optional<Order> retrieveOrderById(@PathVariable String id) throws NotFoundException, MongoException {
        try {
            return orderRepo.findById(id);
        } catch (MongoException ex) {
            throw new NotFoundException(ex.getCode() + " : " + ex.getMessage());
        }
    }

    @GetMapping("/id/{id}/orderlines")
    public List<OrderLine> retrieveOrderLinesByOrderId(@PathVariable String id) throws NotFoundException, MongoException {
        try {
            Optional<Order> _orderOpt = orderRepo.findById(id);
            return _orderOpt.get().getOrderLines();
        } catch (MongoException ex) {
            throw new RuntimeException("Not found");
        }
    }

    @PostMapping("/create")
    public Order createOrder(@RequestBody Order order) {
        try {
            for (OrderLine ol : order.getOrderLines()) {
                orderLineRepo.save(ol);
            }
            return orderRepo.save(order);
        } catch (MongoException ex) {
            throw ex;
        }
    }

    @PutMapping("/id/{id}/status/set/{status}")
    public Order updateOrderStatus(@PathVariable String id, @PathVariable Status status) throws MongoException {
        try {
            Optional<Order> _orderOpt = orderRepo.findById(id);
            if(status.equals(Status.IN_PROGRESS) || status.equals(Status.COMPLETED)) {
                _orderOpt.get().setStatus(status);
                return orderRepo.save(_orderOpt.get());
            } else {
                throw new MongoException("yeet?");
            }
        } catch (MongoException ex) {
            throw new NotFoundException(ex.getCode() + " : " + ex.getMessage());
        }
    }

    @PutMapping("")

    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable String id) {
        try {
            orderRepo.deleteById(id);
            return "Deleted record of " + id;
        } catch (MongoException ex) {
            throw ex;
        }
    }
}
