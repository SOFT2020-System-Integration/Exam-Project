package com.example.demo.controllers;

import com.example.demo.DemoApplication;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.models.Game;
import com.example.demo.repositories.GameCatalogRepository;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RepositoryRestResource
@ResponseBody
@RestController
@RequestMapping("/games")
public class GameCatalogController {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(DemoApplication.class));


    @Autowired
    GameCatalogRepository repo;
    GameCatalogController(GameCatalogRepository repo) {
        this.repo = repo;
    }

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

    /*@GetMapping("/title/{title}")
    public Optional<Game> retrieveProductByTitle(@PathVariable String title) {
        try {
            return repo.findByTitle(title);
        } catch (MongoException ex) {
            throw new NotFoundException(ex.getCode() + " : " + ex.getMessage());
        }
    }
    */

    @PostMapping("")
    public Game createProduct(@RequestBody Game game) {
        try {
            return repo.save(game);
        } catch (MongoException ex) {
            throw ex;
        }
    }

    @DeleteMapping("/{id}")
    public String deleteGame(@PathVariable String id) {
        try {
            repo.deleteById(id);
            return "Deleted record of " + id;
        } catch (MongoException ex) {
            throw ex;
        }
    }

}
