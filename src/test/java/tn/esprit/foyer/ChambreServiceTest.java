package tn.esprit.foyer;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
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
class ChambreServiceTest {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreServiceImpl chambreService;

    @BeforeEach
    void setUp() {
        // No setup needed
    }

    @Test
    void testPourcentageChambreParTypeChambre_NullTypes() {
        // Arrange
        when(chambreRepository.findAll()).thenReturn(List.of());

        // Act
        Map<TypeChambre, Double> result = chambreService.pourcentageChambreParTypeChambre(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(chambreRepository).findAll();
    }

    @Test
    void testPourcentageChambreParTypeChambre_EmptyTypes() {
        // Arrange
        when(chambreRepository.findAll()).thenReturn(List.of());

        // Act
        Map<TypeChambre, Double> result = chambreService.pourcentageChambreParTypeChambre(new TypeChambre[0]);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(chambreRepository).findAll();
    }

    @Test
    void testPourcentageChambreParTypeChambre_NoChambres() {
        // Arrange
        TypeChambre[] types = { TypeChambre.SIMPLE, TypeChambre.DOUBLE, TypeChambre.TRIPLE };
        when(chambreRepository.findAll()).thenReturn(List.of());

        // Act
        Map<TypeChambre, Double> result = chambreService.pourcentageChambreParTypeChambre(types);

        // Assert
        assertNotNull(result);
        assertEquals(0.0, result.get(TypeChambre.SIMPLE));
        assertEquals(0.0, result.get(TypeChambre.DOUBLE));
        assertEquals(0.0, result.get(TypeChambre.TRIPLE));
        verify(chambreRepository).findAll();
    }

    @Test
    void testPourcentageChambreParTypeChambre_NormalCase() {
        // Arrange
        TypeChambre[] types = { TypeChambre.SIMPLE, TypeChambre.DOUBLE, TypeChambre.TRIPLE };

        Chambre chambre1 = new Chambre();
        chambre1.setTypeC(TypeChambre.SIMPLE);

        Chambre chambre2 = new Chambre();
        chambre2.setTypeC(TypeChambre.SIMPLE);

        Chambre chambre3 = new Chambre();
        chambre3.setTypeC(TypeChambre.DOUBLE);

        List<Chambre> chambres = Arrays.asList(chambre1, chambre2, chambre3);
        when(chambreRepository.findAll()).thenReturn(chambres);

        // Act
        Map<TypeChambre, Double> result = chambreService.pourcentageChambreParTypeChambre(types);

        // Assert
        assertNotNull(result);
        assertEquals(66.67, result.get(TypeChambre.SIMPLE), 0.01);
        assertEquals(33.33, result.get(TypeChambre.DOUBLE), 0.01);
        assertEquals(0.0, result.get(TypeChambre.TRIPLE));
        verify(chambreRepository).findAll();
    }
}
