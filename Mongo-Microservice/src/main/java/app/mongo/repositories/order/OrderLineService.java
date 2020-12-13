package app.mongo.repositories.order;

import app.mongo.models.order.Order;
import app.mongo.models.order.OrderLine;
import com.mongodb.MongoException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OrderLineService extends MongoRepository<OrderLine, String>
{

}