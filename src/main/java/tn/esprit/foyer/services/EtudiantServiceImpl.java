package tn.esprit.foyer.services;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.configuration.EntityNotFoundExceptionById;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Reservation;
import tn.esprit.foyer.repository.EtudiantRepository;
import tn.esprit.foyer.repository.FoyerRepository;
import tn.esprit.foyer.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class EtudiantServiceImpl implements  IEtudiantService{



    EtudiantRepository etudiantRepository;


    FoyerRepository foyerRepository;


    ReservationRepository reservationRepository;

    @Override
    public List<Etudiant> retrieveAllEtudiants() {
        return etudiantRepository.findAll();
    }

    @Override
    public Etudiant addEtudiant(Etudiant e) {
        log.info("debut methode addEtudiant");
        // calcul tranche Age selon l'age
        return etudiantRepository.save(e);
    }

    @Override
    public Etudiant updateEtudiant(Etudiant e) {
        log.info("debut methode updateEtudiant");
        return etudiantRepository.save(e);
    }

    @Override
    public Etudiant retrieveEtudiant(Long idEtudiant) {
        Etudiant e = etudiantRepository.findById(idEtudiant).orElse(null);
        log.info("fin methode retrieveEtudiant");

        return e;
    }

    @Override
    public void removeEtudiant(Long idEtudiant) {
        if (!etudiantRepository.existsById(idEtudiant)) {
            throw new EntityNotFoundExceptionById("Invalid Id Etudiant was provided");
        }
            etudiantRepository.deleteById(idEtudiant);
    }

    @Override
    public List<Etudiant> addEtudiants(List<Etudiant> etudiants) {
        log.info("debut methode addEtudiants");
        List<Etudiant> etudiants1 =  etudiantRepository.saveAll(etudiants);
        log.info("fin methode addEtudiants");
        return etudiants1;
    }

    @Override
    public Etudiant affecterEtudiantAReservation(String nomEt, String prenomEt,
                                                 String idReservation) {
        Etudiant e = etudiantRepository.findByNomEtAndPrenomEt( nomEt, prenomEt);
        Reservation r = reservationRepository.findById(idReservation).orElse(null);
           // controle de saisie +
          List<Etudiant> etudiants = new ArrayList<>();
        if (r.getEtudiants()!=null) {
            etudiants.addAll(r.getEtudiants());
        }
            etudiants.add(e);
            r.setEtudiants(etudiants);
           reservationRepository.save(r);
        return e;
    }
}
