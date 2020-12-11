package app.carcatalog.controllers;

import app.carcatalog.models.Game;
import app.carcatalog.models.GameType;
import app.carcatalog.models.Type;
import app.carcatalog.repositories.GameCatalogRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class GameApiFetcher {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(GameApiFetcher.class));

    private GameCatalogRepository repo;
    public GameApiFetcher(GameCatalogRepository repo) {
        this.repo = repo;
    }

    public boolean clearMongoDbAndSaveNewGames() throws IOException, MongoException {
        try{
            repo.deleteAll();
            if(repo.findAll().size() == 0) {
                LOGGER.info("[LOGGER] ::: EMPTIED MONGO DATABASE");
            }
            List<Game> gameList = getGamesFromApi();
            for (Game g : gameList) {
                repo.save(g);
                LOGGER.info("[LOGGER] ::: Saved Game ::: " + g);
            }
            return true;
        } catch(MongoException ex) {
            return false;
        }
    }

    public List<Game> getGamesFromApi() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Response response;
        JsonNode jsonNode;
        List<Game> gameList = new ArrayList<>();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.rawg.io/api/games")
                .method("GET", null)
                .build();
        try {
            response = client.newCall(request).execute();
            jsonNode = objectMapper.readTree(response.body().string());
            List<Type> digitalphysical = Arrays.asList(Type.DIGITAL, Type.PHYSICAL);
            List<Type> digital = Arrays.asList(Type.DIGITAL, Type.PHYSICAL);
            List<Type> physical = Arrays.asList(Type.DIGITAL, Type.PHYSICAL);
            for (JsonNode x: jsonNode.get("results")) {
                float retailPrice = fakeRetailPrice(30, 60);
                float currentPrice = fakeDiscountedPrice(retailPrice, 50, 10);
                gameList.add(new Game(x.get("name").asText(), currentPrice, retailPrice, calculateDiscountPercentage(currentPrice, retailPrice), randomInt(1, 5), randomInt(1, 999), fakeTypeList()));
            }
            return gameList;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private float fakeRetailPrice(float min, float max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max must be greater than Min");
        }
        float price = ThreadLocalRandom.current().nextFloat() * (max - min) + min;
        float priceRounded = (float)Math.round(price * 100.0f) / 100.0f;
        return priceRounded;
    }

    private float fakeDiscountedPrice(float price, float maxDiscountPercentage, float minDiscountPercentage) {
        float percentage = ThreadLocalRandom.current().nextFloat() * (maxDiscountPercentage - minDiscountPercentage) + minDiscountPercentage;
        float percentageRounded = Math.round(percentage * 100.0f) / 100.0f;
        float priceMinusPercentage = (price*percentageRounded) / 100;
        float newPrice = (price - priceMinusPercentage) * 100f;
        return Math.round(newPrice + 100.0f) / 100.0f;
    }

    private List<GameType> fakeTypeList() {
        int rnd = ThreadLocalRandom.current().nextInt(1, 3 + 1);
        if(rnd == 1) {
            return Arrays.asList(new GameType("1", Type.DIGITAL,"Digital copy of the game"), new GameType("2",Type.PHYSICAL, "Physical copy of the game"));
        }
        else if(rnd == 2) {
            return Arrays.asList(new GameType("1", Type.DIGITAL, "Digital copy of the game"));
        }
        else if(rnd == 3) {
            return Arrays.asList(new GameType("2", Type.PHYSICAL, "Physical copy of the game"));
        } else {
            return null;
        }
    }



    private float calculateDiscountPercentage(float lo, float hi) {
        return Math.round((((hi - lo)/hi)*100)*100.0f) / 100.0f;
    }
}
