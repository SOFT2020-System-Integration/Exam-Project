package app.carcatalog.repositories;


import app.carcatalog.models.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface GameCatalogRepository extends MongoRepository<Game, String>
{
    Optional<Game> findByTitle(String title);
}