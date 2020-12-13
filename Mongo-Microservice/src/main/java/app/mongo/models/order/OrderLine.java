package app.mongo.models.order;

import app.mongo.models.game.Game;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class OrderLine {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    String id;
    Status status;
    Game game;

    public OrderLine() {
    }

    public OrderLine(Status status, Game game) {
        this.status = status;
        this.game = game;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", game=" + game +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
