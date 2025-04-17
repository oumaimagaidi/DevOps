package tn.esprit.foyer.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.services.IChambreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chambre")
@CrossOrigin(origins = "http://localhost:4200")
public class ChambreController {
    final IChambreService chambreService;

    @GetMapping("/all")
    public List<Chambre> getAllChambres() {
        return chambreService.retrieveAllChambres();
    }

    @PostMapping("/add")
    public Chambre addChambre(@RequestBody Chambre chambre) {
        return chambreService.addChambre(chambre);
    }

    @PutMapping("/update")
    public Chambre updateChambre(@RequestBody Chambre chambre) {
        return chambreService.updateChambre(chambre);
    }

    @GetMapping("/get/{id}")
    public Chambre getChambre(@PathVariable("id") Long idChambre) {
        return chambreService.retrieveChambre(idChambre);
    }

    @DeleteMapping("/remove/{id}")
    public void removeChambre(@PathVariable("id") Long idChambre) {
        chambreService.removeChambre(idChambre);
    }
}