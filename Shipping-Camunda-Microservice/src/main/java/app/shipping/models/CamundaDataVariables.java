package app.shipping.models;

public class CamundaDataVariables {
    private CamundaDataDefiner game;
    private CamundaDataDefiner type;

    public CamundaDataVariables() {
    }

    public CamundaDataVariables(CamundaDataDefiner game, CamundaDataDefiner type) {
        this.game = game;
        this.type = type;
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
}
