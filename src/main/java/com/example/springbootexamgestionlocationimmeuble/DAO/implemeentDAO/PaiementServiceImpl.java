package com.example.springbootexamgestionlocationimmeuble.DAO.implemeentDAO;

import com.example.springbootexamgestionlocationimmeuble.DAO.PaiementService;
import com.example.springbootexamgestionlocationimmeuble.Entity.Contrat;
import com.example.springbootexamgestionlocationimmeuble.Entity.Paiement;
import com.example.springbootexamgestionlocationimmeuble.Entity.UniteLocation;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;
import com.example.springbootexamgestionlocationimmeuble.repository.PaiementRepository;
import com.example.springbootexamgestionlocationimmeuble.repository.UniteLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;


    public PaiementServiceImpl(PaiementRepository paiementRepository) {
        this.paiementRepository = paiementRepository;
    }

   /* @Override
    public List<Paiement> getPaiementsByContrat(Contrat contrat) {
        return paiementRepository.findByContrat(contrat);
    }*/

    @Override
    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    @Autowired
    private UniteLocationRepository uniteLocationRepository;

    public List<UniteLocation> getUnitesAvecContrat() {
        return uniteLocationRepository.findUnitesWithContrats();
    }


    public List<Paiement> getPaiementsByProprietaire(Utilisateur proprietaire) {
        return paiementRepository.findByProprietaire(proprietaire);
    }

    public List<Paiement> getPaiementsByLocataire(Utilisateur locataire) {
        return paiementRepository.findByLocataire(locataire);
    }

    public Paiement enregistrerPaiement(Paiement paiement) {
        paiement.setPaye(true);
        paiement.setDatePaiement(LocalDate.now());
        return paiementRepository.save(paiement);
    }

    @Override
    public List<Paiement> getPaiementsByContrat(int contrat) {
        return List.of();
    }

    // Génération automatique de paiements mensuels (optionnel)
    public void genererPaiementsMensuels(Contrat contrat) {
        LocalDate debut = contrat.getDateDebut();
        LocalDate fin = contrat.getDateFin();
        while (!debut.isAfter(fin)) {
            Paiement paiement = new Paiement();
            paiement.setContrat(contrat);
            paiement.setDateEcheance(debut);
            paiement.setMontant(contrat.getMontant());
            paiement.setPaye(false);
            paiementRepository.save(paiement);
            debut = debut.plusMonths(1);
        }
    }

  /*  @Override
    public List<Paiement> getPaiementsByContrat(Contrat contrat) {
        return paiementRepository.findByContrat(contrat);
    }*/

    @Override
    public Paiement getPaiementById(int id) {
        return paiementRepository.findById((long) id).orElse(null);
    }

    @Override
    @Transactional
    public void marquerCommePaye(int paiementId) {
        Paiement paiement = paiementRepository.findById((long) paiementId).orElse(null);
        if (paiement != null && (paiement.getPaye() == null || !paiement.getPaye())) {
            paiement.setPaye(true);
            paiement.setStatut(Paiement.Statut.PAYE); // mettre à jour le statut aussi
            paiement.setDatePaiement(LocalDate.now());
            paiementRepository.save(paiement);
        }
    }






    public BigDecimal getCurrentMonthRevenue() {
        YearMonth now = YearMonth.now();
        BigDecimal total = paiementRepository.sumPaidByMonth(now.getYear(), now.getMonthValue());
        return total != null ? total : BigDecimal.valueOf(0.0);
    }

    public double getYearToDateRevenue() {
        int year = LocalDate.now().getYear();
        Double total = paiementRepository.sumPaidByYear(year);
        return total != null ? total : 0.0;
    }

    public long countPaid() {
        return paiementRepository.countByPayeTrue();
    }

    public long countUnpaid() {
        return paiementRepository.countByPayeFalse();
    }

    public long countOverdue() {
        return paiementRepository.countByStatut(Paiement.Statut.EN_RETARD);
    }

    // Labels des 12 derniers mois (ex: Jan, Fév, Mar…)
    public List<String> getLast12MonthsLabels() {
        List<String> labels = new ArrayList<>();
        YearMonth current = YearMonth.now();

        for (int i = 11; i >= 0; i--) {
            YearMonth ym = current.minusMonths(i);
            String monthLabel = ym.getMonth()
                    .getDisplayName(TextStyle.SHORT, Locale.FRENCH) // ou Locale.ENGLISH
                    + " " + ym.getYear();
            labels.add(monthLabel);
        }

        return labels;
    }

    // Revenus des 12 derniers mois
    public List<BigDecimal> getRevenueForLast12Months() {
        List<BigDecimal> revenues = new ArrayList<>();
        YearMonth current = YearMonth.now();
        for (int i = 11; i >= 0; i--) {
            YearMonth ym = current.minusMonths(i);
            BigDecimal total = paiementRepository.sumPaidByMonth(ym.getYear(), ym.getMonthValue());
            revenues.add(total != null ? total : BigDecimal.valueOf(0.0));
        }
        return revenues;
    }



}
