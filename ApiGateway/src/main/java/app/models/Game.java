package app.models;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Game {
    @Id
    private String id;
    @NonNull
    private String title;
    @NonNull
    private float currentPrice;
    @NonNull
    private float retailPrice;
    @NonNull
    private float savingsPercentage;
    @NonNull
    private float rating;
    @NonNull
    private int inStock;
    @NonNull
    private List<GameType> type;

    public Game() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public float getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(float retailPrice) {
        this.retailPrice = retailPrice;
    }

    public float getSavingsPercentage() {
        return savingsPercentage;
    }

    public void setSavingsPercentage(float savingsPercentage) {
        this.savingsPercentage = savingsPercentage;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public List<GameType> getType() {
        return type;
    }

    public void setType(List<GameType> type) {
        this.type = type;
    }
}
