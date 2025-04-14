package tn.esprit.foyer.services;

import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.TypeChambre;

import java.util.List;
import java.util.Map;

public interface IChambreService {
    List<Chambre> retrieveAllChambres();

    Chambre addChambre(Chambre c);

    Chambre updateChambre(Chambre c);

    Chambre retrieveChambre(Long idChambre);

    void removeChambre(Long idChambre);

    List<Chambre> findByTypeCAndBlocIdBloc(TypeChambre typeChambre, Long idBloc);

    List<Chambre> findByReservationsEstValid(Boolean estValid);

    List<Chambre> findByBlocIdBlocAndBlocCapaciteBlocGreaterThan(Long idBloc, Long capaciteBloc);

    List<Chambre> getChambresParNomBloc(String nomBloc);

    long nbChambreParTypeEtBloc(TypeChambre type, long idBloc);

    List<Chambre> getChambresNonReserveParNomFoyerEtTypeChambre(String nomFoyer, TypeChambre type);

    List<Chambre> getChambresParBlocEtType(long idBloc, TypeChambre typeC);

    void pourcentageChambreParTypeChambre();

    Map<TypeChambre, Double> pourcentageChambreParTypeChambre(TypeChambre[] typesChambres);

}
