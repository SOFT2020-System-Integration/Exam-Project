package app.models.shipment;

import lombok.Data;

import java.io.Serializable;

@Data
public class CamundaDataObject implements Serializable {
    String value, type;

    public CamundaDataObject() {}
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
