package tn.esprit.foyer.services;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Slf4j
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
        List<Chambre> chambresDisponibles = new ArrayList<>();
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12, 31);
        Foyer f = foyerRepository.findByNomFoyer(nomFoyer);
        Optional<List<Bloc>> blocsParFoyer = Optional.ofNullable(f.getBlocs());
        if (blocsParFoyer.isPresent()) {
            blocsParFoyer.get().forEach(bloc -> bloc.getChambres().forEach(chambre -> {
                if (chambre.getTypeC().equals(type)) {
                    Long nbReservationChambre = chambreRepository.checkNbReservationsChambre(startDate, endDate, type,
                            chambre.getNumeroChambre());
                    if ((chambre.getTypeC().equals(TypeChambre.SIMPLE) && nbReservationChambre == 0) ||
                            (chambre.getTypeC().equals(TypeChambre.DOUBLE) && nbReservationChambre < 2) ||
                            (chambre.getTypeC().equals(TypeChambre.TRIPLE) && nbReservationChambre < 3)) {
                        chambresDisponibles.add(chambre);
                    }
                }
            }));
        }
        return chambresDisponibles;
    }

    @Scheduled(fixedRate = 60000)
    public void pourcentageChambreParTypeChambre() {
        Integer nbTotalsChambres = chambreRepository.findAll().size();
        log.info("nbTotalsChambres : " + nbTotalsChambres);
        Arrays.stream(TypeChambre.values()).forEach(
                typeChambre -> {
                    Integer nbChambresParType = chambreRepository.nbChambresParType(typeChambre);
                    Double pourcentageParType = (nbChambresParType.doubleValue() / nbTotalsChambres.doubleValue())
                            * 100;
                    log.info("le pourcentage des chambres pour le type " + typeChambre + " est égale à "
                            + pourcentageParType);
                });
    }

    @Override
    public void pourcentageChambreParTypeChambre(TypeChambre... typesChambres) {
        List<Chambre> chambres = chambreRepository.findAll();
        long nbTotalsChambres = chambres.size();
        log.info("nbTotalsChambres : {}", nbTotalsChambres);

        if (nbTotalsChambres == 0) {
            log.info("Aucune chambre disponible.");
            return;
        }

        if (typesChambres == null || typesChambres.length == 0) {
            log.info("Aucun type de chambre spécifié.");
            return;
        }

        for (TypeChambre type : typesChambres) {
            long count = chambreRepository.nbChambresParType(type);
            double percentage = (count * 100.0) / nbTotalsChambres;
            log.info("Le pourcentage des chambres pour le type {} est égale à {}", type, percentage);
        }
    }

    @Override
    public List<Chambre> getChambresParBlocEtType(long idBloc, TypeChambre typeC) {
        return chambreRepository.findByBlocIdBlocAndTypeC(idBloc, typeC);
    }
}
