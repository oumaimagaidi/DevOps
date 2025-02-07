package tn.esprit.foyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.TypeChambre;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {















 //2.   Liste des chambres double d'un bloc donnée
    List<Chambre> findByTypeCAndBlocIdBloc(TypeChambre typeChambre, Long idBloc);

  //  3. Liste des chambres avec des réservations valide ou pas  (le paramètre à envoyé est le boolean estValide)

    List<Chambre> findByReservationsEstValid(Boolean estValid);

  //  4. Liste des chambres appartenant à  un bloc donné avec une capacité supérieur à un nombre donnée envoyé en paramètre

    List<Chambre> findByBlocIdBlocAndBlocCapaciteBlocGreaterThan(Long idBloc, Long capaciteBloc);


    Chambre findByNumeroChambre(Long numeroChambre);

    List<Chambre>  findByBlocNomBloc(String nomBloc);

    @Query("SELECT c FROM Chambre c where c.bloc.nomBloc=:n")
   List<Chambre>  getChambresParNomBloc(@Param("n") String nomBloc);





    @Query("select  count(c) from Chambre c, Bloc b where c.typeC=:type and  b.idBloc=c.bloc.idBloc and b.idBloc= :idBloc")
    long nbChambreParTypeEtBloc(@Param("type") TypeChambre type, @Param("idBloc") long idBloc);













    @Query("select count (c) from Chambre c join c.reservations r where r.anneeUniversitaire between :startDate and  :endDate and c.typeC= :typeChambre and c.numeroChambre= :numeroChambre")
    long checkNbReservationsChambre(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                                    @Param("typeChambre") TypeChambre typeChambre, @Param("numeroChambre") Long numeroChambre);



    @Query("SELECT count (c) from Chambre c where c.typeC= :typeC")
    Integer nbChambresParType(@Param("typeC") TypeChambre typeChambre);









    //2.Liste des chambres double d'un bloc donnée
    @Query("select c FROM Chambre c where c.bloc.idBloc = :idBloc and c.typeC=:typeC ")
    List<Chambre> findByTypeCAndBloc(@Param("typeC") TypeChambre typeChambre, @Param("idBloc") Long idBloc);


   // 3. Liste des chambres avec des réservations valide ou pas  (le paramètre à envoyé est le boolean estValide)

    @Query("select c from Chambre c join c.reservations r where  r.estValid= :valid")
    List<Chambre> findByReservationsValide(@Param("valid") Boolean estValid);


    @Query("select c FROM Chambre c, Bloc b where  c.bloc.idBloc=b.idBloc and b.idBloc= :idBloc and b.capaciteBloc >= :capaciteBloc")
    List<Chambre> findByBlocIdBlocAndBlocCapaciteBloc(@Param("idBloc") Long idBloc, @Param("capaciteBloc") Long capaciteBloc);



}
