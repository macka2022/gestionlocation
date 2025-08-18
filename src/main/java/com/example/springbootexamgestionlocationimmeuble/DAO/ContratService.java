package com.example.springbootexamgestionlocationimmeuble.DAO;

import com.example.springbootexamgestionlocationimmeuble.Entity.Contrat;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;

import java.util.List;
import java.util.Optional;


public interface ContratService {



    //public  List<Contrat> findByLocataire(Optional<Utilisateur> proprietaire);
    public List<Contrat> getAllContrats() ;
    public List<Contrat> findByLocataire(Optional<Utilisateur> proprietaire);
    public Contrat getContratById(int id) ;

    public Contrat saveContrat(Contrat contrat) ;

    public void deleteContrat(int id) ;
    List<Contrat> findByLocataire(Utilisateur locataire);

}
