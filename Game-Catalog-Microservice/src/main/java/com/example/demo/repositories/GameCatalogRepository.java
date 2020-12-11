package com.example.demo.repositories;

import com.example.demo.models.Game;
import com.mongodb.MongoException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface GameCatalogRepository extends MongoRepository<Game, String> {
    List<Game> findAll() throws MongoException;
    //Optional<Game> findByTitle(String title) throws MongoException;
}
