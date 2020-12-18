package app.models.shipment;

import lombok.Data;

import java.io.Serializable;

@Data
public class CamundaGame implements Serializable {
    private Variables variables;
    public CamundaGame() {}
    public Variables getVariables() {
        return variables;
    }
    public void setVariables(Variables variables) {
        this.variables = variables;
    }
}
