package tn.esprit.foyer.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tn.esprit.foyer.entities.Bloc;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.repository.BlocRepository;
import tn.esprit.foyer.repository.ChambreRepository;
import tn.esprit.foyer.repository.FoyerRepository; // Import manquant potentiel
import tn.esprit.foyer.repository.UniversiteRepository; // Import manquant potentiel


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers // Active Testcontainers
@Transactional // Isoler les tests via rollback
public class ChambreServiceIntegrationTest {

    // Déclare et gère un conteneur MySQL pour la classe de test
    @Container
    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("foyer_test_db")
            .withUsername("testuser")
            .withPassword("testpass");

    // Configure dynamiquement Spring pour utiliser le conteneur
    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create"); // Important!
        registry.add("spring.jpa.show-sql", () -> "true");
    }

    @Autowired
    private IChambreService chambreService;

    @Autowired
    private ChambreRepository chambreRepository; // Pour vérifier/insérer des données

    @Autowired
    private BlocRepository blocRepository; // Pour créer des blocs de test

    // Injecter les autres si nécessaire pour créer la hiérarchie complète (Foyer, Universite)
    @Autowired
    private FoyerRepository foyerRepository;
    @Autowired
    private UniversiteRepository universiteRepository;


    private Bloc savedBloc;

    @BeforeEach // Méthode pour initialiser des données avant chaque test
    void setUpDatabase() {
        // Nettoyer explicitement peut être utile si @Transactional ne suffit pas
        // chambreRepository.deleteAll();
        // blocRepository.deleteAll();

        // Créer un bloc commun pour les tests
        Bloc bloc = new Bloc();
        bloc.setNomBloc("BlocIntegrationTest");
        bloc.setCapaciteBloc(50L);
        // Si Foyer est requis par Bloc :
        // Foyer foyer = new Foyer(); foyer.setNomFoyer("FoyerTest"); ... foyerRepository.save(foyer);
        // bloc.setFoyer(foyer);
        savedBloc = blocRepository.save(bloc);
    }

    @Test
    void testPourcentageChambre_WithDataInMySql() {
        // Arrange: Insérer des données réelles dans le conteneur MySQL
        Chambre chSimple1 = new Chambre(); chSimple1.setNumeroChambre(101L); chSimple1.setTypeC(TypeChambre.SIMPLE); chSimple1.setBloc(savedBloc);
        Chambre chSimple2 = new Chambre(); chSimple2.setNumeroChambre(102L); chSimple2.setTypeC(TypeChambre.SIMPLE); chSimple2.setBloc(savedBloc);
        Chambre chDouble1 = new Chambre(); chDouble1.setNumeroChambre(201L); chDouble1.setTypeC(TypeChambre.DOUBLE); chDouble1.setBloc(savedBloc);
        Chambre chTriple1 = new Chambre(); chTriple1.setNumeroChambre(301L); chTriple1.setTypeC(TypeChambre.TRIPLE); chTriple1.setBloc(savedBloc);
        chambreRepository.saveAll(List.of(chSimple1, chSimple2, chDouble1, chTriple1)); // 4 chambres au total

        TypeChambre[] typesToTest = {TypeChambre.SIMPLE, TypeChambre.TRIPLE};

        // Act: Appeler la méthode du service.
        // On ne peut pas vérifier les logs facilement, mais on vérifie que ça s'exécute sans erreur.
        // Le test principal ici est que les appels sous-jacents au repository (findAll, nbChambresParType)
        // fonctionnent correctement contre MySQL.
        assertDoesNotThrow(() -> chambreService.pourcentageChambreParTypeChambre(typesToTest));

        // Vérification Optionnelle (plus pour tester le repository lui-même):
        assertEquals(4, chambreRepository.findAll().size());
        assertEquals(2, chambreRepository.nbChambresParType(TypeChambre.SIMPLE));
        assertEquals(1, chambreRepository.nbChambresParType(TypeChambre.DOUBLE));
        assertEquals(1, chambreRepository.nbChambresParType(TypeChambre.TRIPLE));
    }

    @Test
    void testPourcentageChambre_NoDataInMySql() {
        // Arrange: Assurer qu'aucune chambre n'existe (le @BeforeEach crée un bloc mais pas de chambre)
        assertEquals(0, chambreRepository.findAll().size()); // Vérifier l'état initial

        TypeChambre[] typesToTest = {TypeChambre.SIMPLE};

        // Act & Assert: Vérifier que l'appel ne lève pas d'exception
        assertDoesNotThrow(() -> chambreService.pourcentageChambreParTypeChambre(typesToTest));

        // Vérification Optionnelle (pour confirmer qu'on est bien à 0)
        assertEquals(0, chambreRepository.findAll().size());
    }

}