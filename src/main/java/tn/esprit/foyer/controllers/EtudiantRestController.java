package tn.esprit.foyer.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.services.IEtudiantService;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/etudiant")
@Tag(name = "Gestion des étudiants")
public class EtudiantRestController {
    IEtudiantService etudiantService;
    // http://localhost:8089/foyer/etudiant/retrieve-all-etudiants
    @GetMapping("/retrieve-all-etudiants")
    @Operation(description = "récupérer la liste des étudiants")
    @ResponseBody
    public List<Etudiant> getEtudiants() {
        List<Etudiant> listEtudiants = etudiantService.retrieveAllEtudiants();
        return listEtudiants;
    }

    // http://localhost:8089/foyer/etudiant/retrieve-etudiant/8
    @GetMapping("/retrieve-etudiant/{etudiantId}")
    @Operation(description = "récupérer un étudiant par son id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Etudiant",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Etudiant.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Etudiant not found",
                    content = @Content) })
    @ResponseBody
    public Etudiant retrieveEtudiant(@Parameter(description = "id of student to be searched")
                                         @PathVariable("etudiantId") Long etudiantId) {
        return etudiantService.retrieveEtudiant(etudiantId);
    }

    // http://localhost:8089/foyer/etudiant/add-etudiant
    @PostMapping("/add-etudiant")
    @Operation(description = "ajouter un étudiant")
    @ResponseBody
    public Etudiant addEtudiant(@RequestBody Etudiant e) {
        Etudiant etudiant= etudiantService.addEtudiant(e);
        return etudiant;
    }

    // http://localhost:8089/foyer/etudiant/update-etudiant
    @PutMapping("/update-etudiant")
    @Operation(description = "modifier un étudiant")
    @ResponseBody
    public Etudiant updateEtudiant(@RequestBody Etudiant e) {
        Etudiant etudiant= etudiantService.updateEtudiant(e);
        return etudiant;
    }
    // http://localhost:8089/foyer/etudiant/removeEtudiant
    @DeleteMapping("/removeEtudiant/{idEtudiant}")
    @ResponseBody
    public void removeEtudiant(@PathVariable("idEtudiant") Long idEtudiant) {
        etudiantService.removeEtudiant(idEtudiant);
    }

    // http://localhost:8089/foyer/etudiant/add-etudiants
    @PostMapping("/add-etudiants")
    @Operation(description = "ajouter une liste étudiants")
    @ResponseBody
    public List<Etudiant> addEtudiants (@RequestBody List<Etudiant> etudiants) {
        List<Etudiant> e= etudiantService.addEtudiants(etudiants);
        return e;
    }

    // http://localhost:8089/foyer/etudiant/affecterEtudiantAReservation/test5/test5/4D84888
    @Operation(description = "assigner un étudiant à une résérvation")
    @PutMapping("/affecterEtudiantAReservation/{nomEt}/{prenomEt}/{idReservation}")
    @ResponseBody
    Etudiant affecterEtudiantAReservation(@PathVariable("nomEt") String nomEt, @PathVariable("prenomEt") String prenomEt, @PathVariable("idReservation") String idReservation)
    {
        Etudiant etudiant= etudiantService.affecterEtudiantAReservation(nomEt,prenomEt,idReservation);
        return etudiant;
    }
}
