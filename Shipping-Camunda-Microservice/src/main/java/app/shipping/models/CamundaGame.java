package app.shipping.models;

import lombok.Data;

@Data
public class CamundaGame {
    private Variables variables;

    public CamundaGame() {
    }

    public Variables getVariables() {
        return variables;
    }

    public void setVariables(Variables variables) {
        this.variables = variables;
    }
}
