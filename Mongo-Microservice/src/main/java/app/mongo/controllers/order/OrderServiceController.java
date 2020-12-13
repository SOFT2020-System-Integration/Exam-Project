package app.mongo.controllers.order;

import app.mongo.exceptions.NotFoundException;
import app.mongo.models.game.Game;
import app.mongo.models.order.Order;
import app.mongo.repositories.game.GameService;
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
    private OrderService repo;

    @GetMapping("/")
    public List<Order> retrieveAll() {
        return repo.findAll();
    }




}
