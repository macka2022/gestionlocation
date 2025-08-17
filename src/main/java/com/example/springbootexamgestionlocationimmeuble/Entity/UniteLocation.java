package com.example.springbootexamgestionlocationimmeuble.Entity;

import jakarta.persistence.*;

@Entity
public class UniteLocation {



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String numero;

        private double surface;
    public boolean isOccupied() {
        return this.statut == Statut.OCCUPE;
    }

        private int pieces;

        private double loyer;

        @Enumerated(EnumType.STRING)
        private Statut statut=Statut.DISPONIBLE;

    private boolean occupied;


    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }


        public enum Statut {
            DISPONIBLE, OCCUPE
        }

        @ManyToOne
        @JoinColumn(name = "immeuble_id")
        private Immeuble immeuble;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public double getLoyer() {
        return loyer;
    }

    public void setLoyer(double loyer) {
        this.loyer = loyer;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Immeuble getImmeuble() {
        return immeuble;
    }

    public void setImmeuble(Immeuble immeuble) {
        this.immeuble = immeuble;
    }


}


