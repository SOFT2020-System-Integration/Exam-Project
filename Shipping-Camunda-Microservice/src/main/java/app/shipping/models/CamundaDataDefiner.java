package app.shipping.models;

public class CamundaDataDefiner {
    String value, type;

    public CamundaDataDefiner() {}

    public CamundaDataDefiner(String value, String type) {
        this.value = value;
        this.type = type;
    }

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
