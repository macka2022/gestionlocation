package com.example.springbootexamgestionlocationimmeuble.DAO;

import com.example.springbootexamgestionlocationimmeuble.Entity.Immeuble;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;

import java.util.List;
import java.util.Map;

public interface ImeubleService {
    public List<Immeuble> getAllImmmeuble() ;
    public Immeuble getImmmeubleById(int id) ;
    public Immeuble saveImmmeuble(Immeuble immmeuble) ;

   public void deleteImmeuble(int id);

    public List<Immeuble>  getAllImmeubleByUser(Utilisateur utilisateur);

    public long countAll();
    public long countAllUnits();
    public long countOccupiedUnits();
    public long countAvailableUnits();
    public Map<String, long[]> getOccupancyStats();

}
