package com.example.springbootexamgestionlocationimmeuble.Controller;

import com.example.springbootexamgestionlocationimmeuble.DAO.ImeubleService;
import com.example.springbootexamgestionlocationimmeuble.DAO.UniteLocationService;
import com.example.springbootexamgestionlocationimmeuble.Entity.Immeuble;
import com.example.springbootexamgestionlocationimmeuble.Entity.UniteLocation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/immobilier/unitelocation")
public class UnitedLocationController {
    private final UniteLocationService uniteLocationService;
    private final ImeubleService imeubleService;

    public UnitedLocationController(UniteLocationService uniteLocationService, ImeubleService imeubleService) {
        this.uniteLocationService = uniteLocationService;
        this.imeubleService = imeubleService;
    }

    @GetMapping("/list")
    public String afficherUnitedLocation(Model model) {
        List<UniteLocation> unites = uniteLocationService.getAllUnite(); // ou findAll()
        model.addAttribute("unites", unites);
        return "/locataire/unitelocationlist"; // sans 'main'
    }

    @GetMapping("/add/{id}")
    public String formListeUnitedLocation(Model model , @PathVariable int id) {
        // On récupère l’immeuble par ID
        Immeuble immeuble = imeubleService.getImmmeubleById(id);

        // On prépare une nouvelle unité de location, et on lui assigne l’immeuble
        UniteLocation uniteLocation = new UniteLocation();
        uniteLocation.setImmeuble(immeuble);

        model.addAttribute("unitelocation", uniteLocation);
        model.addAttribute("immeuble", immeuble); // utile d

        return "/proprietaire/addinitedlocation"; // sans 'main'
    }
    @GetMapping("/update/{id}")
    public String updateUnite(@PathVariable int id, Model model) {

        UniteLocation uniteLocation = uniteLocationService.getUniteById(id);

        model.addAttribute("immeubles", imeubleService.getAllImmmeuble());
        model.addAttribute("unitelocation", uniteLocation);
        return "/proprietaire/addinitedlocation"; // s
    }
    @PostMapping("/addnew")
    public String addNewUnitedLocation(@ModelAttribute  UniteLocation unite) {

        if (unite.getStatut() == null) {
            unite.setStatut(UniteLocation.Statut.DISPONIBLE);
        }

              uniteLocationService.saveUnite(unite);
            return "redirect:/immobilier/immeuble/detail/" + unite.getImmeuble().getId();
        }


       // sans 'main'


    @GetMapping ("/delete/{id}")
    public String deleteUnite(@PathVariable int id) {
          uniteLocationService.deleteUnite(id);
        return "redirect:/immobilier/unitelocation/list"; // sans 'main'
    }



    @GetMapping("/resever/{id}")
    public String reserverUnite(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        UniteLocation unite = uniteLocationService.getUniteById(id);

        if (unite == null) {
            redirectAttributes.addFlashAttribute("error", "Unité non trouvée.");
            return "redirect:/immobilier/unitelocation/list";
        }

        if (!"disponible".equalsIgnoreCase(unite.getStatut().name())) {
            redirectAttributes.addFlashAttribute("error", "Cette unité n'est pas disponible pour la réservation.");
            return "redirect:/immobilier/unitelocation/list";
        }

        // Réserver l’unité
        unite.setStatut(UniteLocation.Statut.OCCUPE);
        uniteLocationService.saveUnite(unite);

        redirectAttributes.addFlashAttribute("success", "Unité réservée avec succès.");
        return  "redirect:/immobilier/immeuble/detail/" + unite.getImmeuble().getId();
    }

}
