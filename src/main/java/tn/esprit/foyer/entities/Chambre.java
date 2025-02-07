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
public class Chambre implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long idChambre; // Clé primaire
    Long numeroChambre;
    @Enumerated(EnumType.STRING)
    TypeChambre typeC;







    @OneToMany(fetch = FetchType.EAGER)
    List<Reservation> reservations;
    @ManyToOne
    @JsonIgnore
    Bloc bloc;


}

