package app.repositories;

import app.config.RibbonConfig;
import app.models.game.Game;
import app.models.shipment.CamundaGame;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin
@FeignClient("mongo-service")
@RibbonClient(name = "mongo-service", configuration = RibbonConfig.class)
public interface MongoClient {
    @GetMapping("/games")
    Collection<Game> gameCollection();
}