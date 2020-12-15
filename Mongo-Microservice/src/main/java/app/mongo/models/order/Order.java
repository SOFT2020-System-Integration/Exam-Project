package app.mongo.models.order;

import app.mongo.models.game.Game;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;
import java.util.List;

@Data
@Document(collection ="orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private Date createdAt;
    private Status status;
    private List<OrderLine> orderLines;

    public Order() {
    }

    public Order(Date createdAt, Status status, List<OrderLine> orderLines) {
        this.createdAt = createdAt;
        this.status = status;
        this.orderLines = orderLines;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", status=" + status +
                ", orderLines=" + orderLines +
                '}';
    }
}
