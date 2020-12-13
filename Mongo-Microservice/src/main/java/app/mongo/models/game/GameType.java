package app.mongo.models.game;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Data
public class GameType implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private Type type;
    private String description;

    public GameType() {
    }

    public GameType(String id, Type type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
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

    @Override
    public String toString() {
        return "GameType{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                '}';
    }
}
