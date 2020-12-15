package app.mongo;

import app.mongo.controllers.game.GameApiFetcher;
import app.mongo.controllers.game.GameServiceController;
import app.mongo.models.game.Game;
import app.mongo.models.order.Order;
import app.mongo.models.order.OrderLine;
import app.mongo.models.order.Status;
import app.mongo.repositories.game.GameService;
import app.mongo.repositories.order.OrderLineService;
import app.mongo.repositories.order.OrderService;
import io.github.kaiso.relmongo.annotation.RelMongoAnnotation;
import io.github.kaiso.relmongo.config.EnableRelMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
@EnableRelMongo
public class MongoApplication implements CommandLineRunner {

    @Autowired
    private GameService gameRepo;
    private OrderService orderRepo;
    private OrderLineService orderLineRepo;


    MongoApplication(GameService gameRepo, OrderService orderRepo, OrderLineService orderLineRepo){
        this.gameRepo = gameRepo;
        this.orderRepo = orderRepo;
        this.orderLineRepo = orderLineRepo;
    };

    public static void main(String[] args){SpringApplication.run(MongoApplication.class, args); }

    @Override
    public void run(String ...args) throws Exception{
        GameApiFetcher apiFetcher = new GameApiFetcher(gameRepo);
        apiFetcher.clearMongoDbAndSaveNewGames();
        orderRepo.deleteAll();
        orderLineRepo.deleteAll();

        List<Game> gameList = gameRepo.findAll();

        OrderLine ld1 = new OrderLine(Status.IN_PROGRESS, gameList.get(0));
        OrderLine ld2 = new OrderLine(Status.IN_PROGRESS, gameList.get(1));
        OrderLine ld3 = new OrderLine(Status.IN_PROGRESS, gameList.get(2));

        orderLineRepo.save(ld1);
        orderLineRepo.save(ld2);
        orderLineRepo.save(ld3);


        List<OrderLine> orderLines = Arrays.asList(ld1, ld2);
        List<OrderLine> orderLines2 = Arrays.asList(ld2, ld3);
        List<OrderLine> orderLines3 = Arrays.asList(ld1, ld2, ld3);
        Order order = new Order(new Date(), Status.IN_PROGRESS, orderLines);
        Order order2 = new Order(new Date(), Status.IN_PROGRESS, orderLines2);
        Order order3 = new Order(new Date(), Status.IN_PROGRESS, orderLines3);



        orderRepo.save(order);
        orderRepo.save(order2);
        orderRepo.save(order3);
    }

    private Game toGame(Game g) {
        return g;
    }


}


