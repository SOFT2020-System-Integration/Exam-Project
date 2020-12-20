package app.shipping.models;

public class CamundaDataVariables {
    private CamundaDataDefiner game;
    private CamundaDataDefiner type;
    private CamundaDataDefiner status;
    private CamundaDataDefiner orderlineId;
    private CamundaDataDefiner orderId;

    public CamundaDataVariables() {
    }

    public CamundaDataVariables(CamundaDataDefiner game, CamundaDataDefiner type, CamundaDataDefiner status, CamundaDataDefiner orderlineId, CamundaDataDefiner orderId) {
        this.game = game;
        this.type = type;
        this.status = status;
        this.orderlineId = orderlineId;
        this.orderId = orderId;
    }

    public CamundaDataDefiner getGame() {
        return game;
    }

    public void setGame(CamundaDataDefiner game) {
        this.game = game;
    }

    public CamundaDataDefiner getType() {
        return type;
    }

    public void setType(CamundaDataDefiner type) {
        this.type = type;
    }

    public CamundaDataDefiner getStatus() {
        return status;
    }

    public void setStatus(CamundaDataDefiner status) {
        this.status = status;
    }

    public CamundaDataDefiner getOrderlineId() {
        return orderlineId;
    }

    public void setOrderlineId(CamundaDataDefiner orderlineId) {
        this.orderlineId = orderlineId;
    }

    public CamundaDataDefiner getOrderId() {
        return orderId;
    }

    public void setOrderId(CamundaDataDefiner orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "CamundaDataVariables{" +
                "game=" + game +
                ", type=" + type +
                ", status=" + status +
                ", orderlineId=" + orderlineId +
                ", orderId=" + orderId +
                '}';
    }
}
