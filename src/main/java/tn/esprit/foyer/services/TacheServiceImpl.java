package tn.esprit.foyer.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Tache;
import tn.esprit.foyer.repository.EtudiantRepository;
import tn.esprit.foyer.repository.TacheRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TacheServiceImpl implements ITacheService{

    TacheRepository tacheRepository;
    EtudiantRepository etudiantRepository;
    @Override
    public List<Tache> retrieveAllTaches() {
        return tacheRepository.findAll();
    }

    @Override
    public Tache addTache(Tache t) {
        return tacheRepository.save(t);
    }

    @Override
    public Tache updateTache(Tache t) {
        return tacheRepository.save(t);
    }

    @Override
    public Tache retrieveTache(Long idTache) {
        return tacheRepository.findById(idTache).get();
    }

    @Override
    public void removeTache(Long idTache) {
        tacheRepository.deleteById(idTache);
    }




    @Override
    public List<Tache> addTachesAndAffectToEtudiant(List<Tache> taches, String nomEt, String prenomEt) {
        Etudiant et = etudiantRepository.findByNomEtAndPrenomEt(nomEt,prenomEt);
        taches.forEach(tache -> {
            tache.setEtudiant(et);
          //  tacheRepository.save(tache);
        });
        tacheRepository.saveAll(taches);
        return taches;
    }

    @Override
    public HashMap<String, Float> calculNouveauMontantInscriptionDesEtudiants() {
        HashMap<String, Float> nouveauxMontantsInscription = new HashMap<>();
        etudiantRepository.findAll().forEach(etudiant -> {
            Float ancienMontant= etudiant.getMontantInscription();
            LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1,1);
            LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12,31);
            Float montantTachesAssignesAnneeEnCours = tacheRepository.
                    sommeTacheAnneeEncours(startDate,endDate,etudiant.getIdEtudiant());
            Float nouveauMontant = ancienMontant;
            if (montantTachesAssignesAnneeEnCours!=null) {
                 nouveauMontant = ancienMontant - montantTachesAssignesAnneeEnCours;
            }
            nouveauxMontantsInscription.put(etudiant.getNomEt()+" "+etudiant.getPrenomEt(),
                    nouveauMontant);
        });
        return nouveauxMontantsInscription;
    }

    //@Scheduled(cron = "0 30 14 09 09 *")
    public void updateNouveauMontantInscriptionDesEtudiants() {
        etudiantRepository.findAll().forEach(etudiant -> {
            Float montantInscription= etudiant.getMontantInscription();
            LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1,1);
            LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12,31);
            Float montantTachesAssignesAnneeEnCours = tacheRepository.sommeTacheAnneeEncours(startDate,endDate,etudiant.getIdEtudiant());
            if (montantTachesAssignesAnneeEnCours!=null) {
                montantInscription = montantInscription - montantTachesAssignesAnneeEnCours;
                etudiant.setMontantInscription(montantInscription);
                etudiantRepository.save(etudiant);
            }

        });
    }


}
