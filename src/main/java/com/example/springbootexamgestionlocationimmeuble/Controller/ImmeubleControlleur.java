package com.example.springbootexamgestionlocationimmeuble.Controller;

import com.example.springbootexamgestionlocationimmeuble.DAO.ImeubleService;
import com.example.springbootexamgestionlocationimmeuble.DAO.UniteLocationService;
import com.example.springbootexamgestionlocationimmeuble.DAO.UtilisateurService;
import com.example.springbootexamgestionlocationimmeuble.Entity.Immeuble;
import com.example.springbootexamgestionlocationimmeuble.Entity.UniteLocation;
import com.example.springbootexamgestionlocationimmeuble.Entity.Utilisateur;
import com.example.springbootexamgestionlocationimmeuble.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("immobilier/immeuble")
public class ImmeubleControlleur {
private final ImeubleService immeubleService;
private final UtilisateurService utilisateurService;
private final UtilisateurController utilisateurController;
private final UniteLocationService uniteLocationService;
    public ImmeubleControlleur(ImeubleService immeubleService, UtilisateurService utilisateurService, UtilisateurController utilisateurController, UniteLocationService uniteLocationService) {
        this.immeubleService = immeubleService;
        this.utilisateurService = utilisateurService;
        this.utilisateurController= utilisateurController;
        this.uniteLocationService=uniteLocationService;
    }

    @GetMapping("/add")
    public String afficherFormulaireImmeuble(Model model) {
        model.addAttribute("immeuble", new Immeuble());
        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateur());
        return "proprietaire/addimmeuble"; // sans 'main'
    }
    @GetMapping("/delete/{id}")
    public String supprimerImmeuble(@PathVariable("id") int id) {
        immeubleService.deleteImmeuble(id);
        return "redirect:/immobilier/immeuble/list";
    }
    @GetMapping("locataire/list")
    public String afficherListeImmeuble( Model model) {

        // Appel à ton service
        List<Immeuble> immeubles = immeubleService.getAllImmmeuble();
        model.addAttribute("immeuble", immeubles);
        return  "/locataire/immeublelist";
    }
    @GetMapping("admin/list")
    public String afficherListeImmeubleP( Model model) {

        // Appel à ton service
        List<Immeuble> immeubles = immeubleService.getAllImmmeuble();
        model.addAttribute("immeuble", immeubles);
        return  "/admin/immeublelist";
    }


    @GetMapping("/proprietaire/list")
    public String afficherListImmeubleProprietaire( Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Utilisateur utilisateur = userDetails.getUtilisateur();

        // Appel à ton service
        List<Immeuble> immeubles = immeubleService.getAllImmeubleByUser(utilisateur);
        model.addAttribute("immeuble", immeubles);
        return  "/proprietaire/immeublelist";
    }

    @PostMapping("/addnew")
    public String addNewListeImmeuble(@ModelAttribute  Immeuble immeuble) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Utilisateur utilisateur = userDetails.getUtilisateur();


        immeuble.setProprietaire(utilisateur);
        immeubleService.saveImmmeuble(immeuble);
        return "redirect:/immobilier/immeuble/proprietaire/list";

    }

    @GetMapping("/update/{id}")
    public String updateListeImmeuble(@PathVariable int id, Model model) {

        Immeuble immeuble = immeubleService.getImmmeubleById(id);

        model.addAttribute("utilisateurs", utilisateurService.getAllUtilisateur());
        model.addAttribute("immeuble", immeuble);
        return "/proprietaire/addimmeuble"; // sans 'main'
    }

    @GetMapping("/list/{id}")
    public String oneListeImmeuble(@PathVariable int id, Model model) {
        Immeuble immeuble = immeubleService.getImmmeubleById(id);


        model.addAttribute("immeubl", immeuble);
        return "/proprietaire/addimmeuble"; // cette page doit afficher les infos de 'immeuble'
    }

     @GetMapping("/detail/{id}")
    public String detailListeImmeuble(@PathVariable int id, Model model) {
         Immeuble immeuble = immeubleService.getImmmeubleById(id);

         // Récupération des unités liées à cet immeuble
         List<UniteLocation> unites = uniteLocationService.getUnitesByImmeuble(immeuble);

         // Ajouter les objets au modèle pour Thymeleaf
         model.addAttribute("immeuble", immeuble);
         model.addAttribute("unites", unites);
        return "/proprietaire/detailImmeuble";
     }

    @GetMapping("/locataire/detail/{id}")
    public String detailListeImmeubleL(@PathVariable int id, Model model) {
        Immeuble immeuble = immeubleService.getImmmeubleById(id);

        // Récupération des unités liées à cet immeuble
        List<UniteLocation> unites = uniteLocationService.getUnitesByImmeuble(immeuble);

        // Ajouter les objets au modèle pour Thymeleaf
        model.addAttribute("immeuble", immeuble);
        model.addAttribute("unites", unites);
        return "/locataire/detailImmeuble";
    }

    @GetMapping("/admin/detail/{id}")
    public String detailListeImmeubleA(@PathVariable int id, Model model) {
        Immeuble immeuble = immeubleService.getImmmeubleById(id);

        // Récupération des unités liées à cet immeuble
        List<UniteLocation> unites = uniteLocationService.getUnitesByImmeuble(immeuble);

        // Ajouter les objets au modèle pour Thymeleaf
        model.addAttribute("immeuble", immeuble);
        model.addAttribute("unites", unites);
        return "/admin/detailImmeuble";
    }

}
