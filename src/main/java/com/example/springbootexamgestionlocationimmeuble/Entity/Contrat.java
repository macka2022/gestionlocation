package com.example.springbootexamgestionlocationimmeuble.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Contrat {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private LocalDate date;
        private LocalDate dateDebut;

        private LocalDate dateFin;

        private double montant;

        @ManyToOne
        @JoinColumn(name = "locataire_id")
        private Utilisateur locataire;

        @OneToOne
        @JoinColumn(name = "unite_id")
        private UniteLocation uniteLocation;

        public Contrat(Contrat contratId) {
        }
        public Contrat(){

        }
        public Contrat(int contratId) {
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public LocalDate getDateDebut() {
                return dateDebut;
        }

        public void setDateDebut(LocalDate dateDebut) {
                this.dateDebut = dateDebut;
        }

        public LocalDate getDateFin() {
                return dateFin;
        }

        public void setDateFin(LocalDate dateFin) {
                this.dateFin = dateFin;
        }

        public double getMontant() {
                return montant;
        }

        public void setMontant(double montant) {
                this.montant = montant;
        }

        public Utilisateur getLocataire() {
                return locataire;
        }

        public void setLocataire(Utilisateur locataire) {
                this.locataire = locataire;
        }

        public UniteLocation getUniteLocation() {
                return uniteLocation;
        }

        public void setUniteLocation(UniteLocation uniteLocation) {
                this.uniteLocation = uniteLocation;
        }
}


