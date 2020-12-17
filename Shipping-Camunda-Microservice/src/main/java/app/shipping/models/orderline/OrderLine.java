package app.shipping.models.orderline;

import app.shipping.models.game.Game;
import app.shipping.models.game.GameType;
import ch.qos.logback.core.status.Status;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;


public class OrderLine implements Serializable {
    @Id
    private String id;
    private Game game;
    private int amount;
    private String status;
    private String orderId;

    public OrderLine() {
    }

    public OrderLine(Game game, int amount, String status) {
        this.game = game;
        this.amount = amount;
        this.status = status;
    }

    public OrderLine(String id, Game game, int amount, String status) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", game=" + game +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
