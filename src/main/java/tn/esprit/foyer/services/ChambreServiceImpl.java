package tn.esprit.foyer.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.Bloc;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.repository.BlocRepository;
import tn.esprit.foyer.repository.ChambreRepository;
import tn.esprit.foyer.repository.FoyerRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ChambreServiceImpl implements IChambreService {

    private final ChambreRepository chambreRepository;
    private final BlocRepository blocRepository;
    private final FoyerRepository foyerRepository;

    @Override
    public List<Chambre> retrieveAllChambres() {
        System.out.println("in method retrieveAllChambres");
        return chambreRepository.findAll();
    }

    @Override
    public Chambre addChambre(Chambre c) {
        return chambreRepository.save(c);
    }

    @Override
    public Chambre updateChambre(Chambre c) {
        return chambreRepository.save(c);
    }

    @Override
    public Chambre retrieveChambre(Long idChambre) {
        return chambreRepository.findById(idChambre).orElse(null);
    }

    @Override
    public void removeChambre(Long idChambre) {
        chambreRepository.deleteById(idChambre);
    }

    @Override
    public List<Chambre> findByTypeCAndBlocIdBloc(TypeChambre typeChambre, Long idBloc) {
        return chambreRepository.findByTypeCAndBlocIdBloc(typeChambre, idBloc);
    }

    @Override
    public List<Chambre> findByReservationsEstValid(Boolean estValid) {
        return chambreRepository.findByReservationsValide(estValid);
    }

    @Override
    public List<Chambre> findByBlocIdBlocAndBlocCapaciteBlocGreaterThan(Long idBloc, Long capaciteBloc) {
        return chambreRepository.findByBlocIdBlocAndBlocCapaciteBloc(idBloc, capaciteBloc);
    }

    @Override
    public List<Chambre> getChambresParNomBloc(String nomBloc) {
        return chambreRepository.findByBlocNomBloc(nomBloc);
    }

    @Override
    public long nbChambreParTypeEtBloc(TypeChambre type, long idBloc) {
        return chambreRepository.nbChambreParTypeEtBloc(type, idBloc);
    }

    @Override
    public List<Chambre> getChambresNonReserveParNomFoyerEtTypeChambre(String nomFoyer, TypeChambre type) {
        return chambreRepository.findByBlocFoyerNomFoyerAndTypeCAndReservationsEmpty(nomFoyer, type);
    }

    @Scheduled(fixedRate = 60000)
    public void pourcentageChambreParTypeChambre() {
        Integer nbTotalsChambres = chambreRepository.findAll().size();
        log.info("nbTotalsChambres : {}", nbTotalsChambres);

        if (nbTotalsChambres == 0) {
            log.info("Aucune chambre disponible.");
            return;
        }

        TypeChambre[] typesChambres = TypeChambre.values();
        for (TypeChambre type : typesChambres) {
            Integer nbChambresType = chambreRepository.nbChambresParType(type);
            double pourcentage = (nbChambresType / (double) nbTotalsChambres) * 100;
            log.info("Le pourcentage des chambres pour le type {} est égale à {}", type, pourcentage);
        }
    }

    public void pourcentageChambreParTypeChambre(TypeChambre[] typesChambres) {
        List<Chambre> chambres = chambreRepository.findAll();
        int totalChambres = chambres.size();

        log.info("nbTotalsChambres : {}", totalChambres);

        if (totalChambres == 0) {
            log.info("Aucune chambre disponible.");
            return;
        }

        if (typesChambres.length == 0) {
            log.info("Aucun type de chambre spécifié.");
            return;
        }

        for (TypeChambre type : typesChambres) {
            int nbChambresType = chambreRepository.nbChambresParType(type);
            double pourcentage = (nbChambresType / (double) totalChambres) * 100;
            log.info("Le pourcentage des chambres pour le type {} est égale à {}", type, pourcentage);
        }
    }

    public List<Chambre> getChambresParBlocEtType(long idBloc, TypeChambre typeC) {
        return chambreRepository.findByBlocIdBlocAndTypeC(idBloc, typeC);
    }
}
