package app.mongo;

import app.mongo.controllers.game.GameApiFetcher;
import app.mongo.controllers.game.GameServiceController;
import app.mongo.models.game.Game;
import app.mongo.models.order.Order;
import app.mongo.models.order.OrderLine;
import app.mongo.models.order.Status;
import app.mongo.repositories.game.GameService;
import app.mongo.repositories.order.OrderService;
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
public class MongoApplication implements CommandLineRunner {

    @Autowired
    private GameService gameRepo;
    private OrderService orderRepo;
    MongoApplication(GameService gameRepo, OrderService orderRepo){
        this.gameRepo = gameRepo;
        this.orderRepo = orderRepo;
    };

    public static void main(String[] args){SpringApplication.run(MongoApplication.class, args); }

    @Override
    public void run(String ...args) throws Exception{
        /*
        GameApiFetcher apiFetcher = new GameApiFetcher(repo);
        apiFetcher.clearMongoDbAndSaveNewGames();
        */
        List<Game> gameList = gameRepo.findAll();

        List<OrderLine> orderLines = Arrays.asList(new OrderLine(Status.IN_PROGRESS, gameList.get(0)), new OrderLine(Status.IN_PROGRESS, gameList.get(1)), new OrderLine(Status.IN_PROGRESS, gameList.get(2)));
        Order order = new Order(new Date(), Status.IN_PROGRESS, orderLines);
        orderRepo.save(order);
    }

    private Game toGame(Game g) {
        return g;
    }


}


