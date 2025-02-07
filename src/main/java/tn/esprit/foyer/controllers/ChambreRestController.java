package tn.esprit.foyer.controllers;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.services.IChambreService;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/chambre")

public class ChambreRestController {
    IChambreService chambreService;
    // http://localhost:8089/foyer/chambre/retrieve-all-chambres
    @GetMapping("/retrieve-all-chambres")
    @ResponseBody
    public List<Chambre> getChambres() {
        List<Chambre> listChambres = chambreService.retrieveAllChambres();
        return listChambres;
    }

    // http://localhost:8089/foyer/chambre/retrieve-chambre/8
    @GetMapping("/retrieve-chambre/{chambreId}")
    @ResponseBody
    public Chambre retrieveChambre(@PathVariable("chambreId") Long chambreId) {
        return chambreService.retrieveChambre(chambreId);
    }

    // http://localhost:8089/foyer/chambre/add-chambre
    @PostMapping("/add-chambre")
    @ResponseBody
    public Chambre addChambre(@RequestBody Chambre c) {
        Chambre chambre= chambreService.addChambre(c);
        return chambre;
    }

    // http://localhost:8089/foyer/chambre/update-chambre
    @PutMapping("/update-chambre")
    @ResponseBody
    public Chambre updateChambre(@RequestBody Chambre c) {
        return chambreService.updateChambre(c);
    }
    // http://localhost:8089/foyer/chambre/removeChambre
    @DeleteMapping("/removeChambre/{idChambre}")
    @ResponseBody
    public void removeChambre(@PathVariable("idChambre") Long idChambre) {
        chambreService.removeChambre(idChambre);
    }

    // http://localhost:8089/foyer/chambre/findByTypeCAndBlocIdBloc/DOUBLE/1
    @GetMapping("/findByTypeCAndBlocIdBloc/{typeChambre}/{idBloc}")
    @ResponseBody
    public List<Chambre> findByTypeCAndBlocIdBloc(@PathVariable("typeChambre") TypeChambre t, @PathVariable("idBloc")  Long idBloc) {
        List<Chambre> listChambresByTypeCAndBlocIdBloc = chambreService.findByTypeCAndBlocIdBloc(t,idBloc);
        return listChambresByTypeCAndBlocIdBloc;
    }

    // http://localhost:8089/foyer/chambre/findByReservationsEstValid/true
    @GetMapping("/findByReservationsEstValid/{estValid}")
    @ResponseBody
    public List<Chambre> findByReservationsEstValid(@PathVariable("estValid") Boolean estValid) {
        return chambreService.findByReservationsEstValid(estValid);
    }

    // http://localhost:8089/foyer/chambre/findByBlocIdBlocAndBlocCapaciteBloc/1/100
    @GetMapping("/findByBlocIdBlocAndBlocCapaciteBloc/{idBloc}/{capaciteBloc}")
    @ResponseBody
    public List<Chambre> findByBlocIdBlocAndBlocCapaciteBloc(@PathVariable("idBloc") Long idBloc,@PathVariable("capaciteBloc")  Long capaciteBloc) {
       return chambreService.findByBlocIdBlocAndBlocCapaciteBlocGreaterThan(idBloc,capaciteBloc);
    }


    // http://localhost:8089/foyer/chambre/getChambresParNomBloc/A
    @GetMapping("/getChambresParNomBloc/{nomBloc}")
    @ResponseBody
    public  List<Chambre> getChambresParNomBloc(@PathVariable("nomBloc") String n) {
        return chambreService.getChambresParNomBloc(n);
    }

    // http://localhost:8089/foyer/chambre/nbChambreParTypeEtBloc/DOUBLE/1
    @GetMapping("/nbChambreParTypeEtBloc/{type}/{idBloc}")
    @ResponseBody
    public  long nbChambreParTypeEtBloc(@PathVariable("type") TypeChambre type, @PathVariable("idBloc") long idBloc) {
        return chambreService.nbChambreParTypeEtBloc(type,idBloc);
    }
        // http://localhost:8089/foyer/chambre/getChambresNonReserveParNomFoyerEtTypeChambre/esprit foyer/SIMPLE
        @GetMapping("/getChambresNonReserveParNomFoyerEtTypeChambre/{nomFoyer}/{type}")
        @ResponseBody
        public  List<Chambre> getChambresNonReserveParNomFoyerEtTypeChambre(@PathVariable("nomFoyer") String nomFoyer,@PathVariable("type") TypeChambre type) {
            return chambreService.getChambresNonReserveParNomFoyerEtTypeChambre(nomFoyer,type);
        }

}
