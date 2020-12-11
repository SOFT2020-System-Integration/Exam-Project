package app.controllers;

import app.models.Game;
import app.repositories.GameCatalogSearchClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class GameSearchController {
    GameCatalogSearchClient client;

    public GameSearchController(GameCatalogSearchClient client) {
        this.client = client;
    }

    @GetMapping("games/title/{title}")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client

    public Collection<Game> myGame(@PathVariable String title)
    {
        List<Game> collect = client.readGames()
                .getContent()
                .stream()
                .filter(User -> User.getTitle().equals(title))
                .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("games/all")
    @ResponseBody
    @CrossOrigin(origins = "*") // allow request from any client

    public Collection<Game> myUser()
    {
        List<Game> collect = client.readGames()
                .getContent()
                .stream()
                .collect(Collectors.toList());
        return collect;
    }
}
