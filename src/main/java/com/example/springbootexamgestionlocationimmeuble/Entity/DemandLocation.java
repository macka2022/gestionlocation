package com.example.springbootexamgestionlocationimmeuble.Entity;

import com.example.springbootexamgestionlocationimmeuble.Entity.UniteLocation;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class DemandLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateDemande;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    public enum Statut {
        EN_ATTENTE, APPROUVEE, REFUSEE
    }

    @ManyToOne
    private Utilisateur locataire;

    @ManyToOne
    private UniteLocation unite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Utilisateur getLocataire() {
        return locataire;
    }

    public void setLocataire(Utilisateur locataire) {
        this.locataire = locataire;
    }

    public UniteLocation getUnite() {
        return unite;
    }

    public void setUnite(UniteLocation unite) {
        this.unite = unite;
    }
}
