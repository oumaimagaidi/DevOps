package tn.esprit.foyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.foyer.entities.Bloc;

import java.util.List;

@Repository
public interface BlocRepository extends JpaRepository<Bloc,Long> {











 //1. 1. Liste des blocs d'une université donnée

    List<Bloc> findByFoyerUniversiteIdUniversite(Long idUniversite);

    Bloc findByNomBloc(String nomBloc);














//1. Liste des blocs d'une université donnée JPQL
    @Query("select b FROM Bloc b, Foyer f , Universite u where b.foyer.idFoyer= f.idFoyer " +
            "  and f.universite.idUniversite= u.idUniversite and u.idUniversite = :idUniversite ")
    List<Bloc> findByFoyerUniversite(@PathVariable("idUniversite") Long idUniversite);
}
