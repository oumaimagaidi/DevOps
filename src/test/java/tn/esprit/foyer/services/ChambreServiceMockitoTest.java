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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ChambreServiceMockitoTest {

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private BlocRepository blocRepository;
    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private ChambreServiceImpl chambreService;

    @Test
    void testPourcentageChambreParTypeChambre_NoChambres() {
        // Arrange
        when(chambreRepository.findAll()).thenReturn(Collections.emptyList());
        TypeChambre[] typesToTest = {TypeChambre.SIMPLE};

        // Act
        var result = chambreService.pourcentageChambreParTypeChambre(typesToTest);

        // Assert
        assertTrue(result.isEmpty());
        verify(chambreRepository).findAll();
    }

    @Test
    void testPourcentageChambreParTypeChambre_EmptyTypesArray() {
        // Arrange
        when(chambreRepository.findAll()).thenReturn(List.of(new Chambre()));
        TypeChambre[] typesToTest = {};

        // Act
        var result = chambreService.pourcentageChambreParTypeChambre(typesToTest);

        // Assert
        assertTrue(result.isEmpty());
        verify(chambreRepository).findAll();
    }

    @Test
    void testPourcentageChambreParTypeChambre_NormalCase() {
        // Arrange
        Chambre chambreSimple = new Chambre();
        chambreSimple.setTypeC(TypeChambre.SIMPLE);
        Chambre chambreDouble = new Chambre();
        chambreDouble.setTypeC(TypeChambre.DOUBLE);
        Chambre chambreDouble2 = new Chambre();
        chambreDouble2.setTypeC(TypeChambre.DOUBLE);

        when(chambreRepository.findAll()).thenReturn(List.of(chambreSimple, chambreDouble, chambreDouble2));
        TypeChambre[] typesToTest = {TypeChambre.SIMPLE, TypeChambre.DOUBLE};

        // Act
        var result = chambreService.pourcentageChambreParTypeChambre(typesToTest);

        // Assert
        assertEquals(2, result.size());
        assertEquals(33.33, result.get(TypeChambre.SIMPLE), 0.01);
        assertEquals(66.67, result.get(TypeChambre.DOUBLE), 0.01);
        verify(chambreRepository).findAll();
    }
}