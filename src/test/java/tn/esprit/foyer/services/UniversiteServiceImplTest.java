package tn.esprit.foyer.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.entities.Universite;
import tn.esprit.foyer.repository.FoyerRepository;
import tn.esprit.foyer.repository.UniversiteRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UniversiteServiceImplTest {

    // Mocked dependencies
    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    private AutoCloseable closeable; 


    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testRetrieveAllUniversites() {
        // Arrange
        List<Universite> universites = List.of(new Universite(), new Universite());
        when(universiteRepository.findAll()).thenReturn(universites);

        // Act
        List<Universite> result = universiteService.retrieveAllUniversites();

        // Assert
        assertEquals(2, result.size(), "Should return a list of 2 universities");
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    public void testAddUniversite() {
        // Arrange
        Universite universite = new Universite();
        universite.setNomUniversite("Test Uni");
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Act
        Universite result = universiteService.addUniversite(universite);

        // Assert
        assertNotNull(result, "Saved university should not be null");
        assertEquals("Test Uni", result.getNomUniversite(), "University name should match");
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    public void testUpdateUniversite() {
        // Arrange
        Universite universite = new Universite();
        universite.setNomUniversite("Updated Uni");
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Act
        Universite result = universiteService.updateUniversite(universite);

        // Assert
        assertNotNull(result, "Updated university should not be null");
        assertEquals("Updated Uni", result.getNomUniversite(), "University name should match");
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    public void testRetrieveUniversite_Found() {
        // Arrange
        Long id = 1L;
        Universite universite = new Universite();
        universite.setIdUniversite(id);
        when(universiteRepository.findById(id)).thenReturn(Optional.of(universite));

        // Act
        Universite result = universiteService.retrieveUniversite(id);

        // Assert
        assertNotNull(result, "University should be found");
        assertEquals(id, result.getIdUniversite(), "University ID should match");
        verify(universiteRepository, times(1)).findById(id);
    }

    @Test
    public void testRetrieveUniversite_NotFound() {
        // Arrange
        Long id = 1L;
        when(universiteRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Universite result = universiteService.retrieveUniversite(id);

        // Assert
        assertNull(result, "Should return null if university is not found");
        verify(universiteRepository, times(1)).findById(id);
    }

    @Test
    public void testRemoveUniversite() {
        // Arrange
        Long id = 1L;
        doNothing().when(universiteRepository).deleteById(id);

        // Act
        universiteService.removeUniversite(id);

        // Assert
        verify(universiteRepository, times(1)).deleteById(id);
    }

    @Test
    public void testAffecterFoyerAUniversite() {
        // Arrange
        Long idFoyer = 1L;
        String nomUniversite = "Test University";
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(idFoyer);
        Universite universite = new Universite();
        universite.setNomUniversite(nomUniversite);
        when(foyerRepository.findById(idFoyer)).thenReturn(Optional.of(foyer));
        when(universiteRepository.findByNomUniversite(nomUniversite)).thenReturn(universite);
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Act to be tested
        Universite result = universiteService.affecterFoyerAUniversite(idFoyer, nomUniversite);

        // Assert
        assertEquals(universite, result, "Should return the assigned university");
        assertEquals(nomUniversite, foyer.getUniversite().getNomUniversite(), "Foyer should be assigned to the university");
        verify(foyerRepository, times(1)).findById(idFoyer);
        verify(universiteRepository, times(1)).findByNomUniversite(nomUniversite);
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    public void testDesaffecterFoyerAUniversite() {
        // Arrange
        Long idFoyer = 1L;
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(idFoyer);
        Universite initialUniversite = new Universite();
        foyer.setUniversite(initialUniversite);
        when(foyerRepository.findById(idFoyer)).thenReturn(Optional.of(foyer));
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Act
        Long result = universiteService.desaffecterFoyerAUniversite(idFoyer);

        // Assert
        assertEquals(idFoyer, result, "Should return the foyer ID");
        assertNull(foyer.getUniversite(), "University should be disassociated");
        verify(foyerRepository, times(1)).findById(idFoyer);
        verify(foyerRepository, times(1)).save(foyer);
    }
}
