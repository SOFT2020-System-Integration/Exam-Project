package app.models;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

@Data
public class GameType implements Serializable {
    @Id
    private String id;
    private Type types;
    private String description;

    public GameType() {
    }

    public GameType(String id, Type types, String description) {
        this.id = id;
        this.types = types;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getTypes() {
        return types;
    }

    public void setTypes(Type types) {
        this.types = types;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}