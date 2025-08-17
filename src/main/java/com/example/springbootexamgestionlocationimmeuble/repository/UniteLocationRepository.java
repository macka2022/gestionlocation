package com.example.springbootexamgestionlocationimmeuble.repository;

import com.example.springbootexamgestionlocationimmeuble.Entity.Immeuble;
import com.example.springbootexamgestionlocationimmeuble.Entity.UniteLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniteLocationRepository extends JpaRepository<UniteLocation, Integer> {
    public List<UniteLocation> findByImmeuble(Immeuble immeuble);
    public List<UniteLocation>   findByStatut(UniteLocation.Statut statut);
    @Query("SELECT DISTINCT c.uniteLocation FROM Contrat c")
    List<UniteLocation> findUnitesWithContrats();

    long countByStatut(UniteLocation.Statut statut); // "DISPONIBLE" / "OCCUPE"
    @Query("select u.immeuble.nom, sum(case when u.statut='DISPONIBLE' then 1 else 0 end), sum(case when u.statut='OCCUPE' then 1 else 0 end) from UniteLocation u group by u.immeuble.nom")
    List<Object[]> occupancyByBuilding();

    long countByOccupied(boolean occupied);

    // Compte les unités d’un immeuble spécifique selon l’occupation
    long countByImmeubleAndOccupied(Immeuble immeuble, boolean occupied);

    // Optionnel : récupérer la liste filtrée
    List<UniteLocation> findByImmeubleAndOccupied(Immeuble immeuble, boolean occupied);

}
