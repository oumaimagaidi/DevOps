package tn.esprit.foyer.services;

import tn.esprit.foyer.entities.Bloc;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.repository.ChambreRepository;
import tn.esprit.foyer.repository.FoyerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.foyer.services.ChambreServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChambreServiceImplTest {

    @InjectMocks
    private ChambreServiceImpl chambreService;

    @Mock
    private FoyerRepository foyerRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Test
    public void testGetChambresNonReserveParNomFoyerEtTypeChambre() {
        // Paramètres de recherche
        String nomFoyer = "esprit foyer";
        TypeChambre type = TypeChambre.SIMPLE;

        // Création d'une chambre disponible : SIMPLE avec 0 réservation
        Chambre chambreAvailable = new Chambre();
        chambreAvailable.setIdChambre(1L);
        chambreAvailable.setNumeroChambre(101L);
        chambreAvailable.setTypeC(TypeChambre.SIMPLE);

        // Création d'une chambre réservée : SIMPLE avec 1 réservation (donc non disponible)
        Chambre chambreReserved = new Chambre();
        chambreReserved.setIdChambre(2L);
        chambreReserved.setNumeroChambre(102L);
        chambreReserved.setTypeC(TypeChambre.SIMPLE);

        // Création d'un bloc et affectation des chambres
        Bloc bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc A");
        List<Chambre> chambresBloc = new ArrayList<>();
        chambresBloc.add(chambreAvailable);
        chambresBloc.add(chambreReserved);
        bloc.setChambres(chambresBloc);

        // Création d'un foyer et affectation du bloc
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer(nomFoyer);
        foyer.setBlocs(Arrays.asList(bloc));

        // Simulation de la recherche du foyer par son nom
        when(foyerRepository.findByNomFoyer(nomFoyer)).thenReturn(foyer);

        // Calcul de la période (année en cours)
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        // Pour chambreAvailable : aucune réservation (0)
        when(chambreRepository.checkNbReservationsChambre(startDate, endDate, type, chambreAvailable.getNumeroChambre()))
                .thenReturn(0L);
        // Pour chambreReserved : 1 réservation, donc non disponible pour une chambre SIMPLE
        when(chambreRepository.checkNbReservationsChambre(startDate, endDate, type, chambreReserved.getNumeroChambre()))
                .thenReturn(1L);

        // Appel de la méthode à tester
        List<Chambre> result = chambreService.getChambresNonReserveParNomFoyerEtTypeChambre(nomFoyer, type);

        // Vérification : seule la chambre disponible doit être retournée
        assertEquals(1, result.size());
        assertEquals(chambreAvailable.getIdChambre(), result.get(0).getIdChambre());
    }
}
