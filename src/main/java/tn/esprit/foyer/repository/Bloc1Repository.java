package tn.esprit.foyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import tn.esprit.foyer.entities.Bloc;


public interface Bloc1Repository extends JpaRepository<Bloc,Long> {
}
