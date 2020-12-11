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
    @NonNull
    private Type types;
    @NonNull
    private String description;
}