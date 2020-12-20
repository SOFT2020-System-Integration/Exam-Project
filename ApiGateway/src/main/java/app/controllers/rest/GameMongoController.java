package app.controllers.rest;

import app.exceptions.NotFoundException;
import app.models.game.Game;
import app.models.order.OrderLine;
import app.models.shipment.CamundaGame;
import app.repositories.MongoClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/games")
public class GameMongoController {
    private final MongoClient client;
    public GameMongoController(MongoClient client) {
        this.client = client;
    }

    @GetMapping("")
    @CrossOrigin(origins = "*") // allow request from any client
    public Collection<Game> retrieveAllGames() throws NotFoundException
    {
        List<Game> collect = client.gameCollection()
                .stream()
                .collect(Collectors.toList());
        if(!collect.isEmpty()) {
            return collect;
        } else {
            throw new NotFoundException(String.format("Games not found..."));
        }
    }

    @GetMapping("/id/{id}")
    @CrossOrigin(origins = "*") // allow request from any client
    public Game retrieveGame(@PathVariable String id) throws NotFoundException
    {
        List<Game> collect = client.gameCollection()
                .stream()
                .filter(Game -> Game.getId().equals(id))
                .collect(Collectors.toList());
        if(!collect.isEmpty()) {
            return collect.get(0);
        } else {
            throw new NotFoundException(String.format("Game with id '%s' not found...", id));
        }
    }
}
