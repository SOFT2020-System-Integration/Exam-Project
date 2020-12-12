package app.gamecatalog;

import app.gamecatalog.controllers.GameApiFetcher;
import app.gamecatalog.repositories.GameCatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GameCatalogApplication implements CommandLineRunner {

    @Autowired
    private GameCatalogRepository repo;

    GameCatalogApplication(GameCatalogRepository repo){
        this.repo = repo;
    };

    public static void main(String[] args){SpringApplication.run(GameCatalogApplication.class, args); }

    @Override
    public void run(String ...args) throws Exception{
        GameApiFetcher a = new GameApiFetcher(repo);
        a.clearMongoDbAndSaveNewGames();
    }

}


