package app.shipping.models;

import lombok.Data;

@Data
public class CamundaDataObject {
    private CamundaDataVariables variables;

    public CamundaDataObject() {
    }

    public CamundaDataObject(CamundaDataVariables variables) {
        this.variables = variables;
    }

    public CamundaDataVariables getVariables() {
        return variables;
    }

    public void setVariables(CamundaDataVariables variables) {
        this.variables = variables;
    }
}
