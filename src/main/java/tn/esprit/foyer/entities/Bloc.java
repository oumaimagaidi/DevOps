package tn.esprit.foyer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Bloc implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="idBloc")
    Long idBloc; // Clé primaire
     String nomBloc;
     Long capaciteBloc;
    @OneToMany(mappedBy = "bloc",fetch = FetchType.EAGER)
  //  @JsonIgnore
    List<Chambre> chambres;
    @ManyToOne
    @JsonIgnore
     Foyer foyer;






    @Override
    public String toString() {
        return "Bloc{" +
                "idBloc=" + idBloc +
                ", nomBloc='" + nomBloc + '\'' +
                ", capaciteBloc=" + capaciteBloc +
                ", chambres=" + chambres +
                ", foyer=" + foyer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bloc bloc = (Bloc) o;
        return Objects.equals(idBloc, bloc.idBloc) && Objects.equals(nomBloc, bloc.nomBloc) && Objects.equals(capaciteBloc, bloc.capaciteBloc) && Objects.equals(chambres, bloc.chambres) && Objects.equals(foyer, bloc.foyer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBloc, nomBloc, capaciteBloc, chambres, foyer);
    }


}

