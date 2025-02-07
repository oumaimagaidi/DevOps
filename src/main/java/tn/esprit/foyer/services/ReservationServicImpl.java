package tn.esprit.foyer.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Reservation;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.repository.ChambreRepository;
import tn.esprit.foyer.repository.EtudiantRepository;
import tn.esprit.foyer.repository.ReservationRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
@Slf4j
public class ReservationServicImpl implements IReservationService {

    ReservationRepository reservationRepository;
    ChambreRepository chambreRepository;
    EtudiantRepository etudiantRepository;

    @Override
    public List<Reservation> retrieveAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation addReservation(Reservation r) {
        return reservationRepository.save(r);
    }

    @Override
    public Reservation updateReservation(Reservation r) {
        return reservationRepository.save(r);
    }

    @Override
    public Reservation retrieveReservation(String idReservation) {
        return reservationRepository.findById(idReservation).orElse(null);
    }

    @Override
    public void removeReservation(String idReservation){
            reservationRepository.deleteById(idReservation);

    }

    @Transactional
    public Reservation ajouterReservationEtAssignerAChambreEtAEtudiant(Reservation res, Long numChambre, long cin) {

        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12, 31);
        Etudiant e = etudiantRepository.findByCin(cin);
        Chambre c = chambreRepository.findByNumeroChambre(numChambre);
        // id selon la convention demandé
        res.setIdReservation(numChambre + e.getCin().toString() + LocalDate.now().getYear());
        res.setEstValid(true);
        // affecter étudiants aux réservations
        List<Etudiant> etudiants = new ArrayList<>();
        if (res.getEtudiants() != null) {
            etudiants.addAll(res.getEtudiants());
        }
        etudiants.add(e);
        res.setEtudiants(etudiants);
        if (c.getReservations() != null) {
            Integer reservationSize = reservationRepository.getReservationsCurrentYear(startDate,endDate,numChambre);
            switch (reservationSize) {
                case 0:
                    log.info("case reservation vide");
                    Reservation r = reservationRepository.save(res);
                    c.getReservations().add(r);
                    chambreRepository.save(c);
                    break;
                case 1:
                    log.info("case reservation courante egale à 1");
                    if (c.getTypeC().equals(TypeChambre.DOUBLE) || c.getTypeC().equals(TypeChambre.TRIPLE)) {
                        Reservation r1 = reservationRepository.save(res); // on peut affecter des reservations a la chambre sans la sauvegarder
                        c.getReservations().add(r1);
                        chambreRepository.save(c);

                    }
                    else {
                        log.info("chambre simple déja réservée");
                    }
                    break;
                case 2:
                    log.info("case reservation courante egale à 2");
                    if (c.getTypeC().equals(TypeChambre.TRIPLE)) {
                        Reservation r2 = reservationRepository.save(res); // on peut affecter des reservations a la cambre sans la sauvegarder
                        c.getReservations().add(r2);
                        chambreRepository.save(c);

                    }
                    else {
                        log.info("chambre double déja complete");
                    }
                    break;
                default:
                    log.info("case default");
                    log.info("Capacité chambre atteinte");
            }
        } else {
            Reservation r = reservationRepository.save(res);
            List<Reservation> reservations = new ArrayList<>();
            reservations.add(r);
            c.setReservations(reservations);
            chambreRepository.save(c);
        }
        return null;
    }

    @Override
    public List<Reservation> getReservationParAnneeUniversitaire(LocalDate dateDebut, LocalDate dateFin) {
        return reservationRepository.findByAnneeUniversitaireBetween(dateDebut,dateFin);
    }

  //  @Scheduled(fixedRate = 60000)
    public void nbPlacesDisponibleParChambreAnneeEnCours() {
        LocalDate currentdate = LocalDate.now();
        LocalDate dateDebut = LocalDate.of(currentdate.getYear(),12,31);
        LocalDate dateFin = LocalDate.of(currentdate.getYear(),1,1);
        List<Chambre> chambresDisponibles = chambreRepository.findAll();
        chambresDisponibles.forEach(
                chambre -> {
         //       AtomicReference<Integer> nbChambresOccupes = new AtomicReference<>(0);
                  AtomicReference<Integer> nbChambresOccupes = new AtomicReference<>(0);

                    if(chambre.getReservations()!=null)
                    {
                        List<Reservation> reservations = chambre.getReservations();
                        reservations.stream().forEach(
                                reservation -> {
                                    if (reservation.getEstValid() && reservation.getAnneeUniversitaire().isAfter(dateDebut) && reservation.getAnneeUniversitaire().isBefore(dateFin))
                                       nbChambresOccupes.getAndSet(nbChambresOccupes.get() + 1);
                                }
                        );
                    }
                    if(chambre.getTypeC().equals(TypeChambre.SIMPLE))
                    {
                     log.info("nb places restantes en "+currentdate.getYear()+" pour la chambre "+chambre.getNumeroChambre()
                     + " est égale à " + (1- nbChambresOccupes.get()));
                    }
                    else  if(chambre.getTypeC().equals(TypeChambre.DOUBLE)){
                        log.info("nb places restantes en "+currentdate.getYear()+" pour la chambre "+chambre.getNumeroChambre()
                                + " est égale à " + (2- nbChambresOccupes.get()));
                    }
                    else { // cas triple
                        log.info("nb places restantes en "+currentdate.getYear()+ " pour la chambre "+chambre.getNumeroChambre()
                                + " est égale à " + (3- nbChambresOccupes.get()));
                    }



                }
        );

    }
}
