package tn.esprit.foyer.services;

import tn.esprit.foyer.entities.Etudiant;

import java.util.List;

public interface IEtudiantService {

    List<Etudiant> retrieveAllEtudiants();
    Etudiant addEtudiant(Etudiant e);
    Etudiant updateEtudiant(Etudiant e);
    Etudiant retrieveEtudiant(Long idEtudiant);
    void removeEtudiant(Long idEtudiant);

    List<Etudiant> addEtudiants (List<Etudiant> etudiants);

    Etudiant affecterEtudiantAReservation (String nomEt, String prenomEt, String idReservation) ;

}
