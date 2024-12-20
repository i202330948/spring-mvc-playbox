package pe.edu.cibertec.spring_mvc_playbox.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer modeId;
    private String modeName;

    @OneToMany(mappedBy = "mode", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Game> games;

}
