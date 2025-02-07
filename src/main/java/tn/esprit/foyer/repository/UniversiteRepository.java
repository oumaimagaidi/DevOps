package tn.esprit.foyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.entities.Universite;

import java.util.List;


@Repository
public interface UniversiteRepository extends JpaRepository<Universite,Long> {
    //List<Universite> findByFoyerAnd();
    Universite findByNomUniversite(String nomUniversite);

}
