package app.mongo.models.order;

import app.mongo.models.game.Game;
import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToMany;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.List;

@Data
@Document(collection ="orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @OneToOne(fetch= javax.persistence.FetchType.EAGER, cascade = javax.persistence.CascadeType.PERSIST)
    @JoinProperty(name = "customers")
    private String customer_id;
    private Date createdAt;
    private Status status;
    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinProperty(name = "orderlines")
    private List<OrderLine> orderLines;

    public Order() {
    }

    public Order(String customer_id, Date createdAt, Status status, List<OrderLine> orderLines) {
        this.customer_id = customer_id;
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

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
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
                ", customer_id='" + customer_id + '\'' +
                ", createdAt=" + createdAt +
                ", status=" + status +
                ", orderLines=" + orderLines +
                '}';
    }
}
