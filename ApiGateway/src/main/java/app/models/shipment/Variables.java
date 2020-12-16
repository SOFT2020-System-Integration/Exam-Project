package app.models.shipment;

import java.io.Serializable;

public class Variables implements Serializable {
    private CamundaDataObject game;
    private CamundaDataObject type;
    private CamundaDataObject orderlineId;
    private CamundaDataObject status;





    public Variables() {
    }

    public CamundaDataObject getGame() {
        return game;
    }

    public void setGame(CamundaDataObject game) {
        this.game = game;
    }

    public CamundaDataObject getType() {
        return type;
    }

    public void setType(CamundaDataObject type) {
        this.type = type;
    }

    public CamundaDataObject getOrderlineId() { return orderlineId; }

    public void setOrderlineId(CamundaDataObject orderlineId) { this.orderlineId = orderlineId; }

    public CamundaDataObject getStatus() { return status; }

    public void setStatus(CamundaDataObject status) { this.status = status; }
}
