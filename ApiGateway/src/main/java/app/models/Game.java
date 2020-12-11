package app.models;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Game {
    private String id;
    private String title;
    private float currentPrice;
    private float retailPrice;
    private float savingsPercentage;
    private float rating;
    private int inStock;
    private List<Type> type;

    public Game() {
    }

    public Game(@NonNull String id, @NonNull String title, @NonNull float currentPrice, @NonNull float retailPrice, @NonNull float savingsPercentage, @NonNull float rating, int inStock, @NonNull List<Type> type) {
        this.id = id;
        this.title = title;
        this.currentPrice = currentPrice;
        this.retailPrice = retailPrice;
        this.savingsPercentage = savingsPercentage;
        this.rating = rating;
        this.inStock = inStock;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", currentPrice=" + currentPrice +
                ", retailPrice=" + retailPrice +
                ", savingsPercentage=" + savingsPercentage +
                ", rating=" + rating +
                ", inStock=" + inStock +
                ", type=" + type +
                '}';
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

    public List<Type> getType() {
        return type;
    }

    public void setType(List<Type> type) {
        this.type = type;
    }
}
