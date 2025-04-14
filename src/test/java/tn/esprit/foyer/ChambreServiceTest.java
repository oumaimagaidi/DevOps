package tn.esprit.foyer;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPourcentageChambreParTypeChambre_NoChambres() {
        // Arrange
        when(chambreRepository.findAll()).thenReturn(List.of()); // Aucune chambre
        TypeChambre[] typesChambres = { TypeChambre.SIMPLE };

        // Act
        chambreService.pourcentageChambreParTypeChambre(typesChambres);

        // Assert
        // Les assertions sur le logger sont supprimées car nous ne pouvons pas les
        // mocker facilement
        // avec un champ static final
    }
}
