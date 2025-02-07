package tn.esprit.foyer.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.entities.Universite;
import tn.esprit.foyer.repository.FoyerRepository;
import tn.esprit.foyer.repository.UniversiteRepository;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UniversiteServiceImpl implements IUniversiteService{
    UniversiteRepository universiteRepository;
    FoyerRepository foyerRepository;
    @Override
    public List<Universite> retrieveAllUniversites() {
        log.info("debut methode retrieveAllUniversites");
        return universiteRepository.findAll();
    }

    @Override
    public Universite addUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public Universite updateUniversite(Universite u) {

        log.info("debut methode updateUniversite");
        return universiteRepository.save(u);
    }

    @Override
    public Universite retrieveUniversite(Long idUniversite) {
        return universiteRepository.findById(idUniversite).orElse(null);
    }

    @Override
    public void removeUniversite(Long idUniversite) {
        universiteRepository.deleteById(idUniversite);
    }

    @Override
    public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        // récupérer les objets à partir des primitifs
        Foyer f = foyerRepository.findById(idFoyer).get();
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite);
       // identifier le parent du child
        // parent.setChild()
        f.setUniversite(universite);
        // sauvegarde l'objet modifié
        foyerRepository.save(f);
        log.info("fin methode affecterFoyerAUniversite");
        return universite;
    }

    @Override
    public Long desaffecterFoyerAUniversite(long idFoyer) {
        // t1 = recuperer le temps (date sys)
        Foyer f = foyerRepository.findById(idFoyer).get();
        f.setUniversite(null);
        //
        foyerRepository.save(f);
        // t2 = recuperer le temps (date sys)
        // te= t2-t1

        return f.getIdFoyer();
    }
}
