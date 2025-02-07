package tn.esprit.foyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.foyer.entities.Foyer;

import java.util.List;

public interface FoyerRepository extends JpaRepository<Foyer,Long> {

    List<Foyer> findByUniversiteNomUniversite(String nom);

    List<Foyer> findByBlocsNomBloc(String s);


    List<Foyer> findByUniversiteNomUniversiteAndBlocsNomBloc(String nom,String s);










    Foyer findByNomFoyer(String nomFoyer);




}
