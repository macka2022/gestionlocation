package com.example.springbootexamgestionlocationimmeuble.DAO;



import com.example.springbootexamgestionlocationimmeuble.Entity.DemandLocation;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;

import java.util.List;

public interface DemandLocationService {
    List<DemandLocation> getAllDemandes();
    DemandLocation getDemandeById(int id);
    DemandLocation saveDemande(DemandLocation demande);
    void deleteDemande(int id);
    DemandLocation changerStatut(int id, DemandLocation.Statut statut);
  List<DemandLocation> getDemandesByLocataire(Utilisateur locataire);
}

