package tn.esprit.foyer.services;

import tn.esprit.foyer.entities.Bloc;

import java.util.List;

public interface IBlocService {


    List<Bloc> retrieveAllBlocs();
    Bloc addBloc(Bloc b);
    Bloc updateBloc(Bloc b);
    Bloc retrieveBloc(Long idBloc);
    void removeBloc(Long idBloc);
















    List<Bloc> findByFoyerUniversiteIdUniversite (Long idUniversite);

    Bloc affecterChambresABloc (List<Long> numChambre, String nomBloc) ;

    void listeChambresParBloc();
}
