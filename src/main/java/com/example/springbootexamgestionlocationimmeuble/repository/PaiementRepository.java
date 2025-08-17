package com.example.springbootexamgestionlocationimmeuble.repository;

import com.example.springbootexamgestionlocationimmeuble.Entity.Paiement;
import com.example.springbootexamgestionlocationimmeuble.Entity.Contrat;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    // --- Recherches de base ---
    List<Paiement> findByStatut(Paiement.Statut statut);

    boolean existsByContratIdAndMois(int contratId, String mois);

    List<Paiement> findByContrat(Contrat contrat);

    List<Paiement> findByContratAndPayeFalse(Contrat contrat);

    @Query("SELECT p FROM Paiement p WHERE p.contrat.uniteLocation.immeuble.proprietaire = :proprietaire")
    List<Paiement> findByProprietaire(@Param("proprietaire") Utilisateur proprietaire);

    @Query("SELECT p FROM Paiement p WHERE p.contrat.locataire = :locataire")
    List<Paiement> findByLocataire(@Param("locataire") Utilisateur locataire);

    List<Paiement> findByDatePaiementBetween(LocalDate start, LocalDate end);

    // --- Revenus et statistiques ---
    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM Paiement p WHERE p.datePaiement >= :from AND p.datePaiement <= :to AND p.statut = 'PAYE'")
    BigDecimal revenueBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);

    long countByStatut(Paiement.Statut statut);

    long countByStatutIn(Collection<Paiement.Statut> statut);

    // Revenus par mois (JPQL compatible PostgreSQL)
    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM Paiement p WHERE FUNCTION('YEAR', p.datePaiement) = :year AND FUNCTION('MONTH', p.datePaiement) = :month")
    double getCurrentMonthRevenue(@Param("year") int year, @Param("month") int month);

    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM Paiement p WHERE FUNCTION('YEAR', p.datePaiement) = :year")
    double getYearToDateRevenue(@Param("year") int year);

    // PostgreSQL : récupérer les paiements d’un mois donné
    @Query(value = "SELECT COALESCE(SUM(montant), 0) " +
            "FROM paiement " +
            "WHERE paye = true " +
            "AND EXTRACT(YEAR FROM date_paiement) = :year " +
            "AND EXTRACT(MONTH FROM date_paiement) = :month",
            nativeQuery = true)
    BigDecimal sumPaidByMonth(@Param("year") int year, @Param("month") int month);

    // PostgreSQL : récupérer les paiements d’une année donnée
    @Query(value = "SELECT COALESCE(SUM(montant), 0) " +
            "FROM paiement " +
            "WHERE paye = true " +
            "AND EXTRACT(YEAR FROM date_paiement) = :year",
            nativeQuery = true)
    Double sumPaidByYear(@Param("year") int year);

    long countByPayeTrue();

    long countByPayeFalse();

    // --- Version Native PostgreSQL ---
    @Query(value = "SELECT COALESCE(SUM(montant), 0) " +
            "FROM paiement " +
            "WHERE paye = true " +
            "AND EXTRACT(YEAR FROM date_paiement) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM date_paiement) = EXTRACT(MONTH FROM CURRENT_DATE)",
            nativeQuery = true)
    double getMonthToDateRevenue();

    @Query(value = "SELECT COALESCE(SUM(montant), 0) FROM paiement WHERE paye = true",
            nativeQuery = true)
    double getTotalRevenue();

    // Revenus des 12 derniers mois
    @Query("SELECT FUNCTION('MONTH', p.datePaiement), SUM(p.montant) " +
            "FROM Paiement p WHERE p.paye = true " +
            "AND p.datePaiement >= ?1 GROUP BY FUNCTION('MONTH', p.datePaiement)")
    List<Object[]> revenueByMonth(LocalDate since);

}
