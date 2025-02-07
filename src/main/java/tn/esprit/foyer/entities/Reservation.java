package tn.esprit.foyer.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation implements Serializable {
    @Id
    @Column(name="idReservation",length = 50)
    String idReservation; // Clé primaire
   // @Temporal(TemporalType.DATE)
   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy")
    LocalDate anneeUniversitaire;
    Boolean estValid;
    @ManyToMany()
    @JsonIgnore
    List<Etudiant> etudiants;

}

