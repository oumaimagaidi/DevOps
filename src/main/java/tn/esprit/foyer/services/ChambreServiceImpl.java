package tn.esprit.foyer.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.repository.BlocRepository;
import tn.esprit.foyer.repository.ChambreRepository;
import tn.esprit.foyer.repository.FoyerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ChambreServiceImpl implements IChambreService {

    private final ChambreRepository chambreRepository;
    private final BlocRepository blocRepository;
    private final FoyerRepository foyerRepository;

    @Override
    public List<Chambre> retrieveAllChambres() {
        log.info("Récupération de toutes les chambres");
        return chambreRepository.findAll();
    }

    @Override
    public Chambre addChambre(Chambre c) {
        log.info("Ajout d'une chambre : {}", c);
        return chambreRepository.save(c);
    }

    @Override
    public Chambre updateChambre(Chambre c) {
        log.info("Mise à jour de la chambre : {}", c);
        return chambreRepository.save(c);
    }

    @Override
    public Chambre retrieveChambre(Long idChambre) {
        log.info("Recherche de la chambre avec ID : {}", idChambre);
        return chambreRepository.findById(idChambre).orElse(null);
    }

    @Override
    public void removeChambre(Long idChambre) {
        log.info("Suppression de la chambre avec ID : {}", idChambre);
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

    @Override
    public List<Chambre> getChambresParBlocEtType(long idBloc, TypeChambre typeC) {
        return chambreRepository.findByBlocIdBlocAndTypeC(idBloc, typeC);
    }

    // ✔️ Méthode principale avec Map
    public Map<TypeChambre, Double> calculerPourcentageChambreParType(TypeChambre[] typesChambres) {
        List<Chambre> chambres = chambreRepository.findAll();
        int totalChambres = chambres.size();
        log.info("Nombre total de chambres : {}", totalChambres);

        Map<TypeChambre, Double> pourcentages = new HashMap<>();

        if (totalChambres == 0 || typesChambres.length == 0) {
            log.info("Aucune chambre disponible ou aucun type de chambre spécifié.");
            return pourcentages;
        }

        for (TypeChambre type : typesChambres) {
            int nbChambresType = chambreRepository.nbChambresParType(type);
            double pourcentage = (nbChambresType / (double) totalChambres) * 100;
            pourcentages.put(type, pourcentage);
        }

        return pourcentages;
    }

    // 🔁 Méthode planifiée qui affiche les pourcentages
    @Scheduled(fixedRate = 60000)
    public void pourcentageChambreParTypeChambre() {
        Map<TypeChambre, Double> pourcentages = calculerPourcentageChambreParType(TypeChambre.values());
        pourcentages.forEach((type, pourcentage) -> log.info("Le pourcentage des chambres pour le type {} est : {}%",
                type, pourcentage));
    }

    @Override
    public Map<TypeChambre, Double> pourcentageChambreParTypeChambre(TypeChambre[] typesChambres) {
        List<Chambre> chambres = chambreRepository.findAll();
        log.info("nbTotalsChambres : {}", chambres.size());

        if (chambres.isEmpty()) {
            log.info("Aucune chambre disponible.");
            return Map.of();
        }

        Map<TypeChambre, Long> countByType = chambres.stream()
                .filter(chambre -> List.of(typesChambres).contains(chambre.getTypeC()))
                .collect(Collectors.groupingBy(Chambre::getTypeC, Collectors.counting()));

        Map<TypeChambre, Double> pourcentages = countByType.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (double) (entry.getValue() * 100) / chambres.size()));

        pourcentages.forEach((type, pourcentage) -> log.info("Le pourcentage des chambres pour le type {} est : {}%",
                type, pourcentage));

        return pourcentages;
    }
}
