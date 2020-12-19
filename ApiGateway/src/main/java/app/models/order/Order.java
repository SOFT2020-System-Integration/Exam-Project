package app.models.order;

import app.models.customer.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Order implements Serializable {
    @Id
    private String id;
    private Date createdAt;
    private Status status;
    private Customer customer;
    private List<OrderLine> orderLines;

    public Order() {
    }

    public Order(Date createdAt, Status status, Customer customer, List<OrderLine> orderLines) {
        this.createdAt = createdAt;
        this.status = status;
        this.customer = customer;
        this.orderLines = orderLines;
    }

    public Order(String id, Date createdAt, Status status, Customer customer, List<OrderLine> orderLines) {
        this.id = id;
        this.createdAt = createdAt;
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
    /*
    @Override
    public String toString() {return "ORDER: " + "id: " + id + "\nCreation Date: " + createdAt +  "\nStatus: " + status +  "\nCustomer: " + customer +"\nItems:" + orderLines ;}

 */
}
