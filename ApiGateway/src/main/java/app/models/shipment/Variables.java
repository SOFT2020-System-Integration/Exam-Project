package app.models.shipment;

import java.io.Serializable;

public class Variables implements Serializable {
    private CamundaDataObject game;
    private CamundaDataObject type;

    public Variables() {}
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
}
