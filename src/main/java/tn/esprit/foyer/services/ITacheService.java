package tn.esprit.foyer.services;

import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Tache;

import java.util.HashMap;
import java.util.List;

public interface ITacheService {

    List<Tache> retrieveAllTaches();
    Tache addTache(Tache t);
    Tache updateTache(Tache t);
    Tache retrieveTache(Long idTache);
    void removeTache(Long idTache);

     List<Tache>  addTachesAndAffectToEtudiant (List<Tache> taches, String nomEt, String prenomEt ) ;

    HashMap<String,Float > calculNouveauMontantInscriptionDesEtudiants() ;

    void updateNouveauMontantInscriptionDesEtudiants();

}
