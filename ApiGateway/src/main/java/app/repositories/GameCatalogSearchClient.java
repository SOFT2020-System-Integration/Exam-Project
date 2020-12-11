package app.repositories;

import app.config.RibbonConfig;
import app.models.Game;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@FeignClient("game-catalog")
@RibbonClient(name = "game-catalog", configuration = RibbonConfig.class)
public interface GameCatalogSearchClient {
    @GetMapping("/games")
    Resources<Game> readGames();
}