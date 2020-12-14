package app.mongo.models.order;

import app.mongo.models.game.Game;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Document(collection ="orderlines")
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @OneToOne(fetch= FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinProperty(name = "games")
    private String game_id;
    private int amount;
    private Status status;

    public OrderLine() {
    }

    public OrderLine(String game_id, int amount, Status status) {
        this.game_id = game_id;
        this.amount = amount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
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
                ", game_id='" + game_id + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
}
