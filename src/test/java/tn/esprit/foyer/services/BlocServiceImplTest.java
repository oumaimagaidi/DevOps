package tn.esprit.foyer.services;

import tn.esprit.foyer.services.BlocServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.foyer.entities.Bloc;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.repository.BlocRepository;
import tn.esprit.foyer.repository.ChambreRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlocServiceImplTest {

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    private Bloc bloc1;
    private Bloc bloc2;
    private Chambre chambre1;
    private Chambre chambre2;

    @BeforeEach
    void setUp() {
        bloc1 = new Bloc();
        bloc1.setIdBloc(1L);
        bloc1.setNomBloc("Bloc A");
        bloc1.setCapaciteBloc(100L);

        bloc2 = new Bloc();
        bloc2.setIdBloc(2L);
        bloc2.setNomBloc("Bloc B");
        bloc2.setCapaciteBloc(150L);

        chambre1 = new Chambre();
        chambre1.setNumeroChambre(101L);
        chambre1.setTypeC(TypeChambre.SIMPLE);

        chambre2 = new Chambre();
        chambre2.setNumeroChambre(102L);
        chambre2.setTypeC(TypeChambre.DOUBLE);
    }

    @Test
    void testRetrieveAllBlocs() {
        // Arrange
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));

        // Act
        List<Bloc> result = blocService.retrieveAllBlocs();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Bloc A", result.get(0).getNomBloc());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveBloc() {
        // Arrange
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc1));

        // Act
        Bloc result = blocService.retrieveBloc(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveBloc_NotFound() {
        // Arrange
        when(blocRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Bloc result = blocService.retrieveBloc(99L);

        // Assert
        assertNull(result);
        verify(blocRepository, times(1)).findById(99L);
    }

    @Test
    void testAddBloc() {
        // Arrange
        Bloc newBloc = new Bloc();
        newBloc.setNomBloc("New Bloc");
        newBloc.setCapaciteBloc(200L);

        Bloc savedBloc = new Bloc();
        savedBloc.setIdBloc(3L);
        savedBloc.setNomBloc("New Bloc");
        savedBloc.setCapaciteBloc(200L);

        when(blocRepository.save(newBloc)).thenReturn(savedBloc);

        // Act
        Bloc result = blocService.addBloc(newBloc);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getIdBloc());
        assertEquals("New Bloc", result.getNomBloc());
        verify(blocRepository, times(1)).save(newBloc);
    }

    @Test
    void testUpdateBloc() {
        // Arrange
        Bloc updatedBloc = new Bloc();
        updatedBloc.setIdBloc(1L);
        updatedBloc.setNomBloc("Bloc A Updated");
        updatedBloc.setCapaciteBloc(120L);

        when(blocRepository.save(updatedBloc)).thenReturn(updatedBloc);

        // Act
        Bloc result = blocService.updateBloc(updatedBloc);

        // Assert
        assertNotNull(result);
        assertEquals("Bloc A Updated", result.getNomBloc());
        assertEquals(120L, result.getCapaciteBloc());
        verify(blocRepository, times(1)).save(updatedBloc);
    }

    @Test
    void testRemoveBloc() {
        // Arrange
        doNothing().when(blocRepository).deleteById(1L);

        // Act
        blocService.removeBloc(1L);

        // Assert
        verify(blocRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByFoyerUniversiteIdUniversite() {
        // Arrange
        Long universiteId = 1L;
        when(blocRepository.findByFoyerUniversite(universiteId)).thenReturn(Arrays.asList(bloc1, bloc2));

        // Act
        List<Bloc> result = blocService.findByFoyerUniversiteIdUniversite(universiteId);

        // Assert
        assertEquals(2, result.size());
        verify(blocRepository, times(1)).findByFoyerUniversite(universiteId);
    }

    @Test
    void testAffecterChambresABloc_BlocNotFound() {
        // Arrange
        List<Long> numChambres = Arrays.asList(101L, 102L);
        String nomBloc = "Unknown Bloc";
        when(blocRepository.findByNomBloc(nomBloc)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            blocService.affecterChambresABloc(numChambres, nomBloc);
        });

        assertEquals("Bloc non trouvé avec le nom: " + nomBloc, exception.getMessage());
    }

    @Test
    void testAffecterChambresABloc_ChambreNotFound() {
        // Arrange
        List<Long> numChambres = Arrays.asList(101L, 999L); // 999 n'existe pas
        String nomBloc = "Bloc A";

        when(blocRepository.findByNomBloc(nomBloc)).thenReturn(bloc1);
        when(chambreRepository.findByNumeroChambre(101L)).thenReturn(chambre1);
        when(chambreRepository.findByNumeroChambre(999L)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            blocService.affecterChambresABloc(numChambres, nomBloc);
        });

        assertEquals("Chambre non trouvée avec le numéro: 999", exception.getMessage());
    }

    @Test
    void testListeChambresParBloc() {
        // Arrange
        bloc1.setChambres(Arrays.asList(chambre1, chambre2));
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));

        // Act
        blocService.listeChambresParBloc();

        // Assert
        verify(blocRepository, times(1)).findAll();
    }
}