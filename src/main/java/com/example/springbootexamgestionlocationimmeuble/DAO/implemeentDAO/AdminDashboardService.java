package com.example.springbootexamgestionlocationimmeuble.DAO.implemeentDAO;

import com.example.springbootexamgestionlocationimmeuble.Entity.AdminDashboardVM;
import com.example.springbootexamgestionlocationimmeuble.Entity.Paiement;
import com.example.springbootexamgestionlocationimmeuble.Entity.UniteLocation;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;
import com.example.springbootexamgestionlocationimmeuble.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AdminDashboardService {

    private final UtilisateurRepository utilisateurRepo;
    private final ImmeubleRepository immeubleRepo;
    private final UniteLocationRepository uniteRepo;
    private final ContratRepository contratRepo;
    private final PaiementRepository paiementRepo;

    public AdminDashboardService(UtilisateurRepository utilisateurRepo,
                                 ImmeubleRepository immeubleRepo,
                                 UniteLocationRepository uniteRepo,
                                 ContratRepository contratRepo,
                                 PaiementRepository paiementRepo) {
        this.utilisateurRepo = utilisateurRepo;
        this.immeubleRepo = immeubleRepo;
        this.uniteRepo = uniteRepo;
        this.contratRepo = contratRepo;
        this.paiementRepo = paiementRepo;
    }

    public AdminDashboardVM getSnapshot() {
        var vm = new AdminDashboardVM();

        // --- KPI counts ---
        vm.totalUsers      = utilisateurRepo.count();
        vm.totalOwners     = utilisateurRepo.countByRole(Utilisateur.Role.PROPRIETAIRE);
        vm.totalTenants    = utilisateurRepo.countByRole(Utilisateur.Role.LOCATAIRE);
        vm.totalBuildings  = immeubleRepo.count();
        vm.totalUnits      = uniteRepo.count();
        vm.unitsAvailable  = uniteRepo.countByStatut(UniteLocation.Statut.DISPONIBLE);
        vm.unitsOccupied   = uniteRepo.countByStatut(UniteLocation.Statut.OCCUPE);
        vm.activeContracts = contratRepo.countActive(LocalDate.now());

        // --- Paid / unpaid ---
        vm.overduePayments = paiementRepo.countByStatut(Paiement.Statut.EN_RETARD);
        vm.paidCount       = paiementRepo.countByStatutIn(List.of(Paiement.Statut.PAYE));
        vm.unpaidCount     = paiementRepo.countByStatut(Paiement.Statut.EN_RETARD);

        // --- Revenus ---
        LocalDate now = LocalDate.now();
        LocalDate monthStart = now.withDayOfMonth(1);
        LocalDate monthEnd   = monthStart.plusMonths(1).minusDays(1);
        vm.monthlyRevenue = safe(paiementRepo.revenueBetween(monthStart, monthEnd));

        LocalDate yearStart = now.withDayOfYear(1);
        vm.ytdRevenue = safe(paiementRepo.revenueBetween(yearStart, now));

        // --- Revenus 12 derniers mois ---
        List<YearMonth> last12Months = new ArrayList<>();
        for (int i = 11; i >= 0; i--) {
            last12Months.add(YearMonth.now().minusMonths(i));
        }

        vm.last12Months = last12Months.stream()
                .map(ym -> ym.getMonth().name().substring(0, 3) + " " + ym.getYear())
                .collect(Collectors.toList());

        vm.revenueByMonth = last12Months.stream()
                .map(ym -> {
                    LocalDate from = ym.atDay(1);
                    LocalDate to   = ym.atEndOfMonth();
                    return safe(paiementRepo.revenueBetween(from, to));
                })
                .collect(Collectors.toList());

        // --- Occupation par immeuble ---
        Map<String, long[]> occMap = new LinkedHashMap<>();
        for (Object[] row : uniteRepo.occupancyByBuilding()) {
            String name = (String) row[0];
            long dispo = ((Number) row[1]).longValue();
            long occ   = ((Number) row[2]).longValue();
            occMap.put(name, new long[]{dispo, occ});
        }
        vm.occupancyByBuilding = occMap;

        return vm;
    }

    private BigDecimal safe(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }
}
