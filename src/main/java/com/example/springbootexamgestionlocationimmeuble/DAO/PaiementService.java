package com.example.springbootexamgestionlocationimmeuble.DAO;

import com.example.springbootexamgestionlocationimmeuble.Entity.Contrat;
import com.example.springbootexamgestionlocationimmeuble.Entity.Paiement;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public interface PaiementService {

    List<Paiement> getAllPaiements();
    public BigDecimal getCurrentMonthRevenue();
   public List<Paiement> getPaiementsByProprietaire(Utilisateur proprietaire);
    List<Paiement> getPaiementsByLocataire(Utilisateur locataire);
    public Paiement enregistrerPaiement(Paiement paiement);
    List<Paiement> getPaiementsByContrat(int contrat);
    public void genererPaiementsMensuels(Contrat contrat);
    public Paiement getPaiementById(int id);
    public void marquerCommePaye(int paiementId);
    public long countPaid();
    public long countUnpaid();
    public long countOverdue();
    public List<String> getLast12MonthsLabels();




}
