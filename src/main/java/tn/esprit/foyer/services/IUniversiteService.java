package tn.esprit.foyer.services;

import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Universite;

import java.util.List;

public interface IUniversiteService {
    List<Universite> retrieveAllUniversites();
    Universite addUniversite(Universite u);
    Universite updateUniversite(Universite u);
    Universite retrieveUniversite(Long idUniversite);
    void removeUniversite(Long idUniversite);

    public Universite affecterFoyerAUniversite (long idFoyer, String nomUniversite) ;
    public Long desaffecterFoyerAUniversite (long idFoyer) ;
}
