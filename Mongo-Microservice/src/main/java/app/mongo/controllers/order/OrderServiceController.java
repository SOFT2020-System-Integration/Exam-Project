package app.mongo.controllers.order;

import app.mongo.controllers.customer.CustomerServiceController;
import app.mongo.exceptions.NotFoundException;
import app.mongo.models.order.Order;
import app.mongo.models.order.OrderLine;
import app.mongo.models.order.Status;
import app.mongo.repositories.order.OrderLineService;
import app.mongo.repositories.order.OrderService;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@RepositoryRestController
//@RepositoryRestResource
@ResponseBody
@RestController
@RequestMapping("/orders")
public class OrderServiceController
{

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceController.class);

    @Autowired
    private OrderService orderRepo;
    private OrderLineService orderLineRepo;

    @GetMapping("")
    public List<Order> retrieveAll() {
        return orderRepo.findAll();
    }

    @GetMapping("/id/{id}")
    public Optional<Order> retrieveOrderById(@PathVariable String id) throws NotFoundException, MongoException {
        try {
            return orderRepo.findById(id);
        } catch (MongoException ex) {
            LOGGER.error("[LOGGER] ::: ORDER CONTROLLER ::: " + ex.getCode() + " ::: " + ex.getMessage());
            throw new NotFoundException(ex.getCode() + " : " + ex.getMessage());
        }
    }

    @GetMapping("/id/{id}/orderlines")
    public List<OrderLine> retrieveOrderLinesByOrderId(@PathVariable String id) throws NotFoundException, MongoException {
        try {
            Optional<Order> _orderOpt = orderRepo.findById(id);
            return _orderOpt.get().getOrderLines();
        } catch (MongoException ex) {
            LOGGER.error("[LOGGER] ::: ORDER CONTROLLER ::: " + ex.getCode() + " ::: " + ex.getMessage());
            throw new NotFoundException(ex.getCode() + " : " + ex.getMessage());
        }
    }

    @PostMapping("/create")
    public Order createOrder(@RequestBody Order order) {
        try {
            LOGGER.info("[LOGGER] ::: ORDER CONTROLLER ::: ORDER ::: " + order);
            return orderRepo.save(order);
        } catch (MongoException ex) {
            LOGGER.error("[LOGGER] ::: ORDER CONTROLLER ::: " + ex.getCode() + " ::: " + ex.getMessage());
            throw new NotFoundException(ex.getCode() + " : " + ex.getMessage());
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
                throw new MongoException("Something went wrong.");
            }
        } catch (MongoException ex) {
            LOGGER.error("[LOGGER] ::: ORDER CONTROLLER ::: " + ex.getCode() + " ::: " + ex.getMessage());
            throw new NotFoundException(ex.getCode() + " : " + ex.getMessage());
        }
    } 

    @PutMapping("/id/{orderId}/orderlines/{orderlineId}/status/set/{status}")
    public Order updateOrderLineStatusByOrderAndOrderLineId(@PathVariable String orderId, @PathVariable String orderlineId, @PathVariable Status status) throws MongoException {
        Optional<Order> _orderOpt;
        OrderLine orderLineToUpdate;
        try {
            _orderOpt = orderRepo.findById(orderId);
            System.out.println(_orderOpt);
            if(status.equals(Status.IN_PROGRESS) || status.equals(Status.COMPLETED)) {
                List<OrderLine> list = _orderOpt.get().getOrderLines().stream()
                        .filter(orderLine -> orderLine.getId().equals(orderlineId))
                        .collect(Collectors.toList());
                System.out.println(list);
                orderLineToUpdate = list.get(0);
                orderLineToUpdate.setStatus(status);
                return orderRepo.save(_orderOpt.get());
            } else {
                throw new MongoException("Something went wrong.");
            }
        } catch (MongoException ex) {
            LOGGER.error("[LOGGER] ::: ORDER CONTROLLER ::: " + ex.getCode() + " ::: " + ex.getMessage());
            throw new NotFoundException(ex.getCode() + " : " + ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable String id) {
        try {
            LOGGER.info("[LOGGER] ::: ORDER CONTROLLER ::: ORDER ::: " + id);
            orderRepo.deleteById(id);
            return "Deleted record of " + id;
        } catch (MongoException ex) {
            LOGGER.error("[LOGGER] ::: ORDER CONTROLLER ::: " + ex.getCode() + " ::: " + ex.getMessage());
            throw new NotFoundException(ex.getCode() + " : " + ex.getMessage());
        }
    }
}
