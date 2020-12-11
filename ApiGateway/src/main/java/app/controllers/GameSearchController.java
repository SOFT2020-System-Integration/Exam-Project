package app.controllers;

import app.models.Game;
import app.models.Type;
import app.repositories.GameCatalogSearchClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@RestController
public class GameSearchController {
    GameCatalogSearchClient client;

    public GameSearchController(GameCatalogSearchClient client) {
        this.client = client;
    }

    @GetMapping("/title/{title}")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client

    public Collection<Game> myGame(@PathVariable String title)
    {
        List<Game> collect = client.readGames()
                .getContent()
                .stream()
                .filter(Game -> Game.getTitle().equals(title))
                .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/games")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client

    public Collection<Game> myGame()
    {
        List<Game> collect = client.readGames()
                .getContent()
                .stream()
                .collect(Collectors.toList());
        return collect;
    }
}
