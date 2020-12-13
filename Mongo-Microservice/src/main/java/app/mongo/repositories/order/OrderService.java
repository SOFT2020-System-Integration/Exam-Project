package app.mongo.repositories.order;


import app.mongo.models.game.Game;
import app.mongo.models.order.Order;
import com.mongodb.MongoException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OrderService extends MongoRepository<Order, String>
{
    List<Order> findAll() throws MongoException;
}