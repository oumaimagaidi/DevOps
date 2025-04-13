package tn.esprit.foyer.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.repository.BlocRepository;
import tn.esprit.foyer.repository.ChambreRepository;
import tn.esprit.foyer.repository.FoyerRepository;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Active Mockito
class ChambreServiceMockitoTest {

    @Mock // Créer un mock pour le repository
    private ChambreRepository chambreRepository;

    // Mocker aussi les autres dépendances injectées dans ChambreServiceImpl
    @Mock
    private BlocRepository blocRepository;
    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks // Créer une instance de ChambreServiceImpl et injecter les mocks
    private ChambreServiceImpl chambreService;

    @Test
    void testPourcentageChambreParTypeChambre_NoChambres() {
        // Arrange: Simuler le cas où il n'y a aucune chambre
        when(chambreRepository.findAll()).thenReturn(Collections.emptyList());
        TypeChambre[] typesToTest = {TypeChambre.SIMPLE};

        // Act: Appeler la méthode à tester
        chambreService.pourcentageChambreParTypeChambre(typesToTest);

        // Assert: Vérifier les interactions avec le mock
        verify(chambreRepository, times(1)).findAll();
        // S'assurer que nbChambresParType n'est JAMAIS appelé car total est 0
        verify(chambreRepository, never()).nbChambresParType(any(TypeChambre.class));
    }

    @Test
    void testPourcentageChambreParTypeChambre_EmptyTypesArray() {
        // Arrange: Simuler au moins une chambre existante, mais un tableau de types vide
        when(chambreRepository.findAll()).thenReturn(List.of(new Chambre())); // Une chambre fictive suffit
        TypeChambre[] typesToTest = {}; // Tableau vide

        // Act
        chambreService.pourcentageChambreParTypeChambre(typesToTest);

        // Assert
        verify(chambreRepository, times(1)).findAll();
        // S'assurer que nbChambresParType n'est JAMAIS appelé car le tableau est vide
        verify(chambreRepository, never()).nbChambresParType(any(TypeChambre.class));
    }

    @Test
    void testPourcentageChambreParTypeChambre_NormalCase() {
        // Arrange: Simuler un scénario normal
        // - 3 chambres au total
        // - On veut tester les pourcentages pour SIMPLE et DOUBLE
        // - Supposons qu'il y ait 2 SIMPLE et 1 DOUBLE

        // Simuler le retour de findAll()
        when(chambreRepository.findAll()).thenReturn(List.of(new Chambre(), new Chambre(), new Chambre())); // 3 chambres fictives

        // Simuler le retour de nbChambresParType pour les types demandés
        when(chambreRepository.nbChambresParType(TypeChambre.SIMPLE)).thenReturn(2);
        when(chambreRepository.nbChambresParType(TypeChambre.DOUBLE)).thenReturn(1);

        TypeChambre[] typesToTest = {TypeChambre.SIMPLE, TypeChambre.DOUBLE};

        // Act
        chambreService.pourcentageChambreParTypeChambre(typesToTest);

        // Assert
        // Vérifier que findAll a été appelé une fois
        verify(chambreRepository, times(1)).findAll();
        // Vérifier que nbChambresParType a été appelé pour SIMPLE
        verify(chambreRepository, times(1)).nbChambresParType(TypeChambre.SIMPLE);
        // Vérifier que nbChambresParType a été appelé pour DOUBLE
        verify(chambreRepository, times(1)).nbChambresParType(TypeChambre.DOUBLE);
        // S'assurer qu'il n'a PAS été appelé pour TRIPLE (car non demandé)
        verify(chambreRepository, never()).nbChambresParType(TypeChambre.TRIPLE);
    }
}