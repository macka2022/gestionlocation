package com.example.springbootexamgestionlocationimmeuble.Controller;



import com.example.springbootexamgestionlocationimmeuble.DAO.ImeubleService;
import com.example.springbootexamgestionlocationimmeuble.DAO.PaiementService;
import com.example.springbootexamgestionlocationimmeuble.DAO.UtilisateurService;
import com.example.springbootexamgestionlocationimmeuble.DAO.implemeentDAO.AdminDashboardService;
import com.example.springbootexamgestionlocationimmeuble.Entity.AdminDashboardVM;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final UtilisateurService userService;
    private final ImeubleService buildingService;
    private final PaiementService paymentService;

    public AdminDashboardController(UtilisateurService userService,
                                    ImeubleService buildingService,
                                    PaiementService paymentService) {
        this.userService = userService;
        this.buildingService = buildingService;
        this.paymentService = paymentService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        AdminDashboardVM vm = new AdminDashboardVM();

        // Exemple d'initialisation (à adapter selon tes services)
        vm.setTotalUsers(userService.countAll());
        vm.setTotalOwners(userService.countByRole(Utilisateur.Role.LOCATAIRE));
        vm.setTotalTenants(userService.countByRole(Utilisateur.Role.PROPRIETAIRE));

        vm.setTotalBuildings(buildingService.countAll());
        vm.setTotalUnits(buildingService.countAllUnits());
        vm.setUnitsOccupied(buildingService.countOccupiedUnits());
        vm.setUnitsAvailable(buildingService.countAvailableUnits());

        vm.setMonthlyRevenue(paymentService.getCurrentMonthRevenue());
        //vm.setYtdRevenue(paymentService.getYearToDateRevenue());

        vm.setPaidCount(paymentService.countPaid());
        vm.setUnpaidCount(paymentService.countUnpaid());
        vm.setOverduePayments(paymentService.countOverdue());

        // Graphiques : 12 derniers mois
        vm.setLast12Months(paymentService.getLast12MonthsLabels());
       // vm.setRevenueByMonth(paymentService.getRevenueForLast12Months());

        // Occupation par immeuble
        vm.setOccupancyByBuilding(buildingService.getOccupancyStats());

        // 👉 Très important : ajouter "vm" au modèle
        model.addAttribute("vm", vm);

        return "admin/dashboard";
    }
}


