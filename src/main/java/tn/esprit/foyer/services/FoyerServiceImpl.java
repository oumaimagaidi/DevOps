package tn.esprit.foyer.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.repository.BlocRepository;
import tn.esprit.foyer.repository.FoyerRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FoyerServiceImpl implements  IFoyerService{

    FoyerRepository foyerRepository;
    BlocRepository blocRepository;

    @Override
    public List<Foyer> retrieveAllFoyers() {
        return foyerRepository.findAll();
    }

    @Override
    public Foyer addFoyer(Foyer f) {

        return foyerRepository.save(f);
    }

    @Override
    public Foyer addFoyerWithBloc(Foyer f) {

        Foyer foyer = foyerRepository.save(f);
        f.getBlocs().forEach(bloc ->
        {
            bloc.setFoyer(foyer);
            blocRepository.save(bloc);
        });
        return foyer;
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
}
