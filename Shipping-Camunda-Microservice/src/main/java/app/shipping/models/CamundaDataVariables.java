package app.shipping.models;

public class CamundaDataVariables {
    private CamundaDataDefiner game;
    private CamundaDataDefiner type;
    private CamundaDataDefiner status;
    private CamundaDataDefiner orderlineId;

    public CamundaDataVariables() {
    }

    public CamundaDataVariables(CamundaDataDefiner game, CamundaDataDefiner type,CamundaDataDefiner status, CamundaDataDefiner orderLineId) {
        this.game = game;
        this.type = type;
        this.status = status;
        this.orderlineId = orderLineId;
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
}
