package tn.esprit.foyer;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.repository.ChambreRepository;
import tn.esprit.foyer.services.ChambreServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ChambreServiceTest {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreServiceImpl chambreService;

    @BeforeEach
    void setUp() {
        // Pas besoin d'initialisation supplémentaire
    }

    @Test
    void testPourcentageChambreParTypeChambre_NoChambres() {
        // Arrange
        when(chambreRepository.findAll()).thenReturn(List.of());
        TypeChambre[] typesChambres = { TypeChambre.SIMPLE };

        // Act
        Map<TypeChambre, Double> result = chambreService.pourcentageChambreParTypeChambre(typesChambres);

        // Assert
        assertTrue(result.isEmpty(), "La map doit être vide si aucune chambre");
        verify(chambreRepository).findAll();
    }

    @Test
    void testPourcentageChambreParTypeChambre_WithChambres() {
        // Arrange
        Chambre chambreSimple = new Chambre();
        chambreSimple.setTypeC(TypeChambre.SIMPLE);
        Chambre chambreDouble = new Chambre();
        chambreDouble.setTypeC(TypeChambre.DOUBLE);

        when(chambreRepository.findAll()).thenReturn(List.of(chambreSimple, chambreDouble));
        TypeChambre[] typesChambres = { TypeChambre.SIMPLE, TypeChambre.DOUBLE };

        // Act
        Map<TypeChambre, Double> result = chambreService.pourcentageChambreParTypeChambre(typesChambres);

        // Assert
        assertEquals(2, result.size(), "La map doit contenir deux entrées");
        assertEquals(50.0, result.get(TypeChambre.SIMPLE), "Le pourcentage des chambres simples doit être 50%");
        assertEquals(50.0, result.get(TypeChambre.DOUBLE), "Le pourcentage des chambres doubles doit être 50%");
        verify(chambreRepository).findAll();
    }

    @Test
    void testCalculerPourcentageChambreParType_NoChambres() {
        // Arrange
        when(chambreRepository.findAll()).thenReturn(List.of());
        TypeChambre[] typesChambres = { TypeChambre.SIMPLE };

        // Act
        Map<TypeChambre, Double> result = chambreService.calculerPourcentageChambreParType(typesChambres);

        // Assert
        assertTrue(result.isEmpty(), "La map doit être vide si aucune chambre.");
        verify(chambreRepository).findAll();
        verify(chambreRepository, never()).nbChambresParType(any());
    }

    @Test
    void testCalculerPourcentageChambreParType_WithData() {
        // Arrange
        when(chambreRepository.findAll()).thenReturn(List.of(new Chambre(), new Chambre(), new Chambre()));
        when(chambreRepository.nbChambresParType(TypeChambre.SIMPLE)).thenReturn(2);
        when(chambreRepository.nbChambresParType(TypeChambre.DOUBLE)).thenReturn(1);

        TypeChambre[] typesChambres = { TypeChambre.SIMPLE, TypeChambre.DOUBLE };

        // Act
        Map<TypeChambre, Double> result = chambreService.calculerPourcentageChambreParType(typesChambres);

        // Assert
        assertEquals(2, result.size());
        assertEquals(66.67, result.get(TypeChambre.SIMPLE), 0.01);
        assertEquals(33.33, result.get(TypeChambre.DOUBLE), 0.01);

        verify(chambreRepository).nbChambresParType(TypeChambre.SIMPLE);
        verify(chambreRepository).nbChambresParType(TypeChambre.DOUBLE);
    }
}
