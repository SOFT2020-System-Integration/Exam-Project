package app.mongo.models.order;

import app.mongo.models.customer.Customer;
import app.mongo.models.game.Game;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.kaiso.relmongo.annotation.*;
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
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinProperty(name = "customers")
    private Customer customer;
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinProperty(name = "orderlines")
    private List<OrderLine> orderLines;

    public Order() {
    }

    public Order(String id, Status status, Customer customer, List<OrderLine> orderLines) {
        this.id = id;
        this.createdAt = new Date();
        this.status = status;
        this.customer = customer;
        this.orderLines = orderLines;
    }

    public Order(Status status, Customer customer, List<OrderLine> orderLines) {
        this.createdAt = new Date();
        this.status = status;
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
                ", customer=" + customer +
                ", orderLines=" + orderLines +
                '}';
    }
}
