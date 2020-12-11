package com.example.demo;

import com.example.demo.controllers.GameApiFetcherController;
import com.example.demo.repositories.GameCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(DemoApplication.class));

    @Autowired
    GameCatalogRepository repo;

    DemoApplication(GameCatalogRepository repo) {
        this.repo = repo;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        GameApiFetcherController a = new GameApiFetcherController(repo);
        a.clearMongoDbAndSaveNewGames();
    }
}
