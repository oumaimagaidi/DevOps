package tn.esprit.foyer.services;

import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.repository.FoyerRepository;

import java.util.List;

public class Foyer1ServiceImpl implements IFoyerService {


    FoyerRepository foyerRepository;
    @Override
    public List<Foyer> retrieveAllFoyers() {
        return foyerRepository.findAll();
    }

    @Override
    public Foyer addFoyer(Foyer f) {
        return foyerRepository.save(f);
    }

    @Override
    public Foyer updateFoyer(Foyer f) {
        return foyerRepository.save(f);
    }

    @Override
    public Foyer retrieveFoyer(Long idFoyer) {
        return foyerRepository.findById(idFoyer).orElse(null);
    }

    @Override
    public void removeFoyer(Long idFoyer) {
            foyerRepository.deleteById(idFoyer);
    }

    @Override
    public Foyer addFoyerWithBloc(Foyer f) {
        return null;
    }
}
