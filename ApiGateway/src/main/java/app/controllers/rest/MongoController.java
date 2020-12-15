package app.controllers.rest;

import app.models.game.Game;
import app.repositories.MongoClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/games")
public class MongoController {
    MongoClient client;

    public MongoController(MongoClient client) {
        this.client = client;
    }

    @GetMapping("/title/{title}")
    @CrossOrigin(origins = "*") // allow request from any client
    public Collection<Game> myGame(@PathVariable String title)
    {
        List<Game> collect = client.readGames()
                .stream()
                .filter(Game -> Game.getTitle().equals(title))
                .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("")
    @CrossOrigin(origins = "*") // allow request from any client

    public Collection<Game> myGame()
    {
        List<Game> collect = client.readGames()
                .stream()
                .collect(Collectors.toList());
        return collect;
    }
}
