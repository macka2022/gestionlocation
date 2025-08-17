package com.example.springbootexamgestionlocationimmeuble.Entity;

import jakarta.persistence.*;

import javax.management.relation.Role;
import java.time.LocalDate;

@Entity
public class Utilisateur {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    private String prenom;

    @Column(unique = true)
    private String email;

    private String telephone;

    private boolean active = true;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDate dateInscrit;

    public enum Role {
        ADMIN, LOCATAIRE, PROPRIETAIRE
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getDateInscrit() {
        return dateInscrit;
    }

    public void setDateInscrit(LocalDate dateInscrit) {
        this.dateInscrit = dateInscrit;
    }
}
