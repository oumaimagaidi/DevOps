package tn.esprit.foyer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Foyer implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    Long idFoyer; // Clé primaire
    @Column(nullable = false)
    String nomFoyer;
    Long capaciteFoyer;
    @OneToMany(mappedBy ="foyer",  cascade = CascadeType.ALL)
    // @JsonIgnore
    List<Bloc> blocs;
    @OneToOne
    Universite universite;

}

