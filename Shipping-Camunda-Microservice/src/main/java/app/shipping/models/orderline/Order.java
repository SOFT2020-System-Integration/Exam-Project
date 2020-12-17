package app.shipping.models.orderline;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private String id;
    private Date createdAt;
    private String status;
    private List<OrderLine> orderLines;

    public Order() {
    }

    public Order(Date createdAt, String status, List<OrderLine> orderLines) {
        this.createdAt = createdAt;
        this.status = status;
        this.orderLines = orderLines;
    }

    public Order(String id, Date createdAt, String status, List<OrderLine> orderLines) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
