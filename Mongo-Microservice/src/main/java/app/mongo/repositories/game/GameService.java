package app.mongo.repositories.game;


import app.mongo.models.game.Game;
import com.mongodb.MongoException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface GameService extends MongoRepository<Game, String>
{
    List<Game> findAll() throws MongoException;
    Game findByTitle(String title) throws MongoException;
}