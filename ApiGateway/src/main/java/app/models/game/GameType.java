package app.models.game;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
public class GameType implements Serializable {
    @Id
    private String id;
    private Type type;
    private String description;

    public GameType() {
    }

    public GameType(Type type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}