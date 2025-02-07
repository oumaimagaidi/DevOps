package tn.esprit.foyer.services;

import tn.esprit.foyer.entities.Bloc;

import java.util.List;

public class Bloc1ServiceImpl implements IBlocService{


    @Override
    public List<Bloc> retrieveAllBlocs() {
        return null;
    }

    @Override
    public Bloc addBloc(Bloc b) {
        return null;
    }

    @Override
    public Bloc updateBloc(Bloc b) {
        return null;
    }

    @Override
    public Bloc retrieveBloc(Long idBloc) {
        return null;
    }

    @Override
    public void removeBloc(Long idBloc) {

    }

    @Override
    public List<Bloc> findByFoyerUniversiteIdUniversite(Long idUniversite) {
        return null;
    }

    @Override
    public Bloc affecterChambresABloc(List<Long> numChambre, String nomBloc) {
        return null;
    }

    @Override
    public void listeChambresParBloc() {

    }
}
