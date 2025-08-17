package com.example.springbootexamgestionlocationimmeuble.DAO;

import com.example.springbootexamgestionlocationimmeuble.Entity.Immeuble;
import com.example.springbootexamgestionlocationimmeuble.Entity.UniteLocation;

import java.util.List;

public interface UniteLocationService {
    public List<UniteLocation> getAllUnite() ;
    public UniteLocation getUniteById(int id) ;
    public UniteLocation saveUnite(UniteLocation unite) ;
    public List<UniteLocation> getUnitesByImmeuble(Immeuble immeuble) ;
    public void deleteUnite(int id);
    public List<UniteLocation> getUnitesDisponibles() ;
}
