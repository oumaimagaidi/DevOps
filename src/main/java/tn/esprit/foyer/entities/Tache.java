package tn.esprit.foyer.entities;


import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class Tache implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTache; // Clé primaire
    @NonNull
    private LocalDate dateTache;
    @NonNull
     Integer duree;
     Float tarifHoraire;
     @Enumerated(EnumType.STRING)
     TypeTache typeTache;

    @ManyToOne()
    Etudiant etudiant;
    @OneToOne(mappedBy = "tache")
    Etudiant etudiantResponsable;





}

