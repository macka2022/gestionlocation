package com.example.springbootexamgestionlocationimmeuble.DAO;

import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;

import javax.management.relation.Role;
import java.util.List;

public interface UtilisateurService {
    public List<Utilisateur> getAllUtilisateur() ;
    public Utilisateur getUtilisateurById(int id) ;
    public Utilisateur saveUtilisateur(Utilisateur utilisateur) ;
    public void updateUtilisateur(Utilisateur utilisateur) ;
    long countAll();
    long countByRole(Utilisateur.Role role);

}
