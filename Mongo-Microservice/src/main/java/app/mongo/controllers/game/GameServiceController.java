package app.mongo.controllers.game;

import app.mongo.exceptions.NotFoundException;
import app.mongo.models.game.Game;
import app.mongo.repositories.game.GameService;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@RepositoryRestController
//@RepositoryRestResource
@ResponseBody
@RestController
@RequestMapping("/games")
public class GameServiceController
{
    @Autowired
    private GameService repo;

    @GetMapping("")
    public List<Game> retrieveAll() {
        return repo.findAll();
    }

    @GetMapping("/id/{id}")
    public Optional<Game> retrieveProductById(@PathVariable String id) throws NotFoundException, MongoException {
        try {
            return repo.findById(id);
        } catch (MongoException ex) {
            throw new NotFoundException(ex.getCode() + " : " + ex.getMessage());
        }
    }

    @GetMapping("/title/{title}")
    public Game retrieveProductByTitle(@PathVariable String title) throws NotFoundException, MongoException {
        try {
            return repo.findByTitle(title);
        } catch (MongoException ex) {
            throw new NotFoundException(ex.getCode() + " : " + ex.getMessage());
        }
    }

    @PostMapping("/create")
    public Game createProduct(@RequestBody Game game) {
        try {
            return repo.save(game);
        } catch (MongoException ex) {
            throw ex;
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteGame(@PathVariable String id) {
        try {
            repo.deleteById(id);
            return "Deleted record of " + id;
        } catch (MongoException ex) {
            throw ex;
        }
    }
}
