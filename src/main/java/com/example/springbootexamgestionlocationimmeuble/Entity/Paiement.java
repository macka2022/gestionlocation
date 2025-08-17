package com.example.springbootexamgestionlocationimmeuble.Entity;


import jakarta.persistence.*;



import java.time.LocalDate;

    @Entity
    public class Paiement {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String mois; // format YYYY-MM

        private double montant;


        private LocalDate datePaiement;
            private Boolean paye = false;
            private LocalDate dateEcheance;
                @Enumerated(EnumType.STRING)
            private Statut statut;
             public enum Statut {
            PAYE, EN_RETARD
        }



        @ManyToOne
        @JoinColumn(name = "contrat_id")
        private Contrat contrat;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Boolean getPaye() {
        return paye;
    }

    public void setPaye(Boolean paye) {
        paye = paye;
    }

    public LocalDate getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(LocalDate dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }
}


