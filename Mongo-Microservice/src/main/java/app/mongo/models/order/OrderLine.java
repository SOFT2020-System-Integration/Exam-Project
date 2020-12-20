package app.mongo.models.order;

import app.mongo.models.game.Game;
import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToOne;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@Document(collection ="orderlines")
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @OneToOne(fetch= FetchType.EAGER, cascade = CascadeType.NONE)
    @JoinProperty(name = "games")
    private Game game;
    private int amount;
    private Status status;

    public OrderLine() {
    }

    public OrderLine(String id, Game game, int amount, Status status) {
        this.id = id;
        this.game = game;
        this.amount = amount;
        this.status = status;
    }

    public OrderLine(Game game, int amount, Status status) {
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
