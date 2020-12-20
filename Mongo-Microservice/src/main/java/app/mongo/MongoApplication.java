package app.mongo;

import app.mongo.controllers.game.GameApiFetcher;
import app.mongo.helpers.Encrypt;
import app.mongo.models.customer.Customer;
import app.mongo.models.game.Game;
import app.mongo.models.order.Order;
import app.mongo.models.order.OrderLine;
import app.mongo.models.order.Status;
import app.mongo.repositories.customer.CustomerService;
import app.mongo.repositories.game.GameService;
import app.mongo.repositories.order.OrderLineService;
import app.mongo.repositories.order.OrderService;
import io.github.kaiso.relmongo.config.EnableRelMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

@EnableDiscoveryClient
@SpringBootApplication
@EnableRelMongo
public class MongoApplication implements CommandLineRunner {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(String.valueOf(MongoApplication.class));

    @Autowired
    private final GameService gameRepo;
    private final OrderService orderRepo;
    private final OrderLineService orderLineRepo;
    private final CustomerService customerRepo;

    MongoApplication(GameService gameRepo, OrderService orderRepo, OrderLineService orderLineRepo, CustomerService customerRepo){
        this.gameRepo = gameRepo;
        this.orderRepo = orderRepo;
        this.orderLineRepo = orderLineRepo;
        this.customerRepo = customerRepo;
    };

    public static void main(String[] args){SpringApplication.run(MongoApplication.class, args); }

    @Override
    public void run(String ...args) throws Exception{
        customerRepo.deleteAll();
        orderRepo.deleteAll();
        orderLineRepo.deleteAll();
        gameRepo.deleteAll();

        GameApiFetcher apiFetcher = new GameApiFetcher(gameRepo);
        apiFetcher.clearMongoDbAndSaveNewGames();

        Customer c1 = new Customer("joHn", "Doe", "john@doe.com", "1234");
        Customer c2 = new Customer("jANe", "Doe", "JaNe@doe.com", "abcd");
        Customer c3 = new Customer("Thomas", "Ebsen", "thomas@doe.com", "abcd");
        customerRepo.save(c1);
        customerRepo.save(c2);
        customerRepo.save(c3);

        List<Game> productList = gameRepo.findAll();
        OrderLine ld1 = new OrderLine(productList.get(0),2, Status.IN_PROGRESS);
        OrderLine ld2 = new OrderLine(productList.get(1),2, Status.IN_PROGRESS);
        OrderLine ld3 = new OrderLine(productList.get(2),2, Status.IN_PROGRESS);


        List<OrderLine> orderLines = Arrays.asList(ld1, ld2);
        List<OrderLine> orderLines2 = Arrays.asList(ld2, ld3);
        List<OrderLine> orderLines3 = Arrays.asList(ld1, ld2, ld3);
        Customer cust1 = customerRepo.findByMail("john@doe.com");
        Customer cust2 = customerRepo.findByMail("jane@doe.com");
        Customer cust3 = customerRepo.findByMail("thomas@doe.com");
        Order order = new Order(Status.IN_PROGRESS, cust1, orderLines);
        Order order2 = new Order(Status.IN_PROGRESS, cust2, orderLines2);
        Order order3 = new Order(Status.IN_PROGRESS, cust3, orderLines3);
        orderRepo.save(order);
        orderRepo.save(order2);
        orderRepo.save(order3);

        for (Customer c : customerRepo.findAll()) {
            LOGGER.log(Level.INFO, "[LOGGER] ::: SAVED CUSTOMER ::: " + c);
        }

        for (Order o: orderRepo.findAll()) {
            LOGGER.log(Level.INFO, "[LOGGER] ::: SAVED ORDER ::: " + o);
        }
    }


}


