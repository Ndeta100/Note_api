package io.ndeta.springwithnextjs.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.ndeta.springwithnextjs.enumeration.Level;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
@Entity
@Data
public class Note implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private  Long id;
    @NotNull(message = "Title of this note cannot be null")
    @NotEmpty(message = "Title of this note cannot be empty")
    private  String title;
    @NotNull(message = "Description of this note cannot be null")
    @NotEmpty(message = "Description of this note cannot be empty")
    private String description;
    private Level level;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss",timezone = "Europe/Tallinn")
    private LocalDateTime createdAt;
}
