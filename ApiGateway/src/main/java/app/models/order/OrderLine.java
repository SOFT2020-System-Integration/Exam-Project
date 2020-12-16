package app.models.order;

import app.models.game.Game;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class OrderLine implements Serializable {
    @Id
    private String id;
    private Game game;
    private int amount;
    private Status status;

    public OrderLine() {
    }

    public OrderLine(Game game, int amount, Status status) {
        this.game = game;
        this.amount = amount;
        this.status = status;
    }

    public OrderLine(String id, Game game, int amount, Status status) {
        this.id = id;
        this.game = game;
        this.amount = amount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "id='" + id + '\'' +
                ", game=" + game +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
}
