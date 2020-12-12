package app.controllers;

import app.models.game.Game;
import app.repositories.GameCatalogClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/games")
public class GameCatalogController {
    GameCatalogClient client;

    public GameCatalogController(GameCatalogClient client) {
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

    @GetMapping("")
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
