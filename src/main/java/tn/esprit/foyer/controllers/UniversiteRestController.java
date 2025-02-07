package tn.esprit.foyer.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.foyer.entities.Universite;
import tn.esprit.foyer.services.IUniversiteService;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/universite")
public class UniversiteRestController {
    IUniversiteService universiteService;
    // http://localhost:8089/foyer/universite/retrieve-all-universites
    @GetMapping("/retrieve-all-universites")
    @ResponseBody
    public List<Universite> getUniversites() {
        List<Universite> listUniversites = universiteService.retrieveAllUniversites();
        return listUniversites;
    }

    // http://localhost:8089/foyer/universite/retrieve-universite/8
    @GetMapping("/retrieve-universite/{universiteId}")
    @ResponseBody
    public Universite retrieveUniversite(@PathVariable("universiteId") Long universiteId) {
        return universiteService.retrieveUniversite(universiteId);
    }

    // http://localhost:8089/foyer/universite/add-universite
    @PostMapping("/add-universite")
    @ResponseBody
    public Universite addUniversite(@RequestBody Universite u) {
        Universite universite= universiteService.addUniversite(u);
        return universite;
    }

    // http://localhost:8089/foyer/universite/update-universite
    @PutMapping("/update-universite")
    @ResponseBody
    public Universite updateUniversite(@RequestBody Universite u) {
        Universite universite= universiteService.updateUniversite(u);
        return universite;
    }
    // http://localhost:8089/foyer/universite/removeUniversite
    @DeleteMapping("/removeUniversite/{idUniversite}")
    @ResponseBody
    public void removeUniversite(@PathVariable("idUniversite") Long idUniversite) {
        universiteService.removeUniversite(idUniversite);
    }

    // http://localhost:8089/foyer/universite/affecterFoyerAUniversite/1/esprit
    @PutMapping("/affecterFoyerAUniversite/{idFoyer}/{nomUniversite}")
    @ResponseBody
    public Universite affecterFoyerAUniversite (@PathVariable("idFoyer") long idFoyer,
                                                @PathVariable("nomUniversite") String nomUniversite) {
        return universiteService.affecterFoyerAUniversite(idFoyer,nomUniversite);
    }

    // http://localhost:8089/foyer/universite/desaffecterFoyerAUniversite/1/1
    @PutMapping("/desaffecterFoyerAUniversite/{idFoyer}/{idUniversite}")
    @ResponseBody
    public Universite desaffecterFoyerAUniversite (@PathVariable("idFoyer") long idFoyer) {
        universiteService.desaffecterFoyerAUniversite(idFoyer);
        return null;
    }

}
