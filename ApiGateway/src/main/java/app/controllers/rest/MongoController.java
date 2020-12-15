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

    @GetMapping("/id/{id}")
    @CrossOrigin(origins = "*") // allow request from any client
    public Game myGame(@PathVariable String id)
    {
        List<Game> collect = client.gameCollection()
                .stream()
                .filter(Game -> Game.getId().equals(id))
                .collect(Collectors.toList());
        return collect.get(0);
    }

    @GetMapping("")
    @CrossOrigin(origins = "*") // allow request from any client

    public Collection<Game> myGame()
    {
        List<Game> collect = client.gameCollection()
                .stream()
                .collect(Collectors.toList());
        return collect;
    }
}
