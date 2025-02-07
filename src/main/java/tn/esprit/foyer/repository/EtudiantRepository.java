package tn.esprit.foyer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.foyer.entities.Etudiant;


@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {


















Etudiant findByCin(Long idEtudiant);
Etudiant findByNomEtAndPrenomEt(String nomEt, String prenomEt);
//@Query(value = "select * from  reservation_etudiants re where re.etudiants_id_etudiant=:idEtudiant", nativeQuery = true)
  //  List<Reservation> rechercheAncienneReservag
}
