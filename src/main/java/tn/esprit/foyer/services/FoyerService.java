package tn.esprit.foyer.services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.repository.FoyerRepository;

import java.util.List;

@Builder
@Service
@AllArgsConstructor
public class FoyerService implements IFoyerService{

    FoyerRepository foyerRepository;

    @Override
    public List<Foyer> retrieveAllFoyers() {

        System.out.println("in method retrieveAllFoyers");
        Foyer foyer1= Foyer.builder().capaciteFoyer(12L).nomFoyer("A").build();
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
        //return foyerRepository.findById(idFoyer).orElse(null);
        return foyerRepository.findById(idFoyer).get();
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
