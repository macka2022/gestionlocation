package com.example.springbootexamgestionlocationimmeuble.Controller;

import com.example.springbootexamgestionlocationimmeuble.DAO.ContratService;
import com.example.springbootexamgestionlocationimmeuble.DAO.PaiementService;
import com.example.springbootexamgestionlocationimmeuble.Entity.Contrat;
import com.example.springbootexamgestionlocationimmeuble.Entity.Paiement;
import com.example.springbootexamgestionlocationimmeuble.Entity.UniteLocation;
import com.example.springbootexamgestionlocationimmeuble.repository.ContratRepository;
import com.example.springbootexamgestionlocationimmeuble.repository.UniteLocationRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;



    @Controller
    @RequestMapping("/paiements")
    public class PaimentController {

        @Autowired
        private PaiementService paiementService;
        @Autowired
        UniteLocationRepository uniteLocationRepository;
@Autowired
        ContratService contratService;
        @Autowired
        ContratRepository contratRepository;
        @GetMapping("/contrat")
        public String listePaiements(Model model) {

            model.addAttribute("paiements", paiementService.getAllPaiements());

            return "admin/listpaiement"; // Thymeleaf : src/main/resources/templates/paiements/liste.html
        }

        // Génération d’un reçu PDF
        @GetMapping("/recu/{id}")
        public void genererRecuPDF(@PathVariable int id, HttpServletResponse response) throws IOException, DocumentException {
            Paiement paiement = paiementService.getPaiementById(id);
            if (paiement != null) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=recu_paiement_" + id + ".pdf");

                Document doc = new Document();
                PdfWriter.getInstance(doc, response.getOutputStream());
                doc.open();
                doc.add(new Paragraph("Reçu de Paiement"));
                doc.add(new Paragraph("nom : " + paiement.getContrat().getLocataire().getNom()));
                doc.add(new Paragraph("Entite : " + paiement.getContrat().getUniteLocation().getNumero()));
                doc.add(new Paragraph("Contrat ID : " + paiement.getContrat().getId()));
                doc.add(new Paragraph("Debut Contrat : " + paiement.getContrat().getDateFin()));
                doc.add(new Paragraph("Fin Contrat : " + paiement.getContrat().getDateDebut()));
                doc.add(new Paragraph("Montant : " + paiement.getMontant()));
                doc.add(new Paragraph("Date de Paiement : " + paiement.getDatePaiement()));
                doc.close();
            }
        }

        // Marquer un paiement comme payé
        @GetMapping("/payer/{id}")
        public String marquerCommePaye(@PathVariable int id, RedirectAttributes redirectAttributes) {
            paiementService.marquerCommePaye(id);
            redirectAttributes.addFlashAttribute("success", "Paiement marqué comme payé.");
            return "redirect:/paiements/details/" + id;
        }


        // Ajouter un paiement manuellement (GET form)
        @GetMapping("/add/{contratId}")
        public String ajouterPaiementForm(@PathVariable int contratId, Model model) {
            Paiement paiement = new Paiement();
            paiement.setContrat(new Contrat(contratId)); // setter temporaire pour lier le contrat
            paiement.setDateEcheance(LocalDate.now().withDayOfMonth(5));





            Contrat contrat = contratService.getContratById(contratId);
            paiement.setContrat(contrat); // On associe le contrat

            paiement.setDateEcheance(LocalDate.now().withDayOfMonth(5));

            // Liste des unités liées à des contrats
            List<UniteLocation> unitesAvecContrat = uniteLocationRepository.findUnitesWithContrats();





            model.addAttribute("uniteLocation", unitesAvecContrat);// exemple
            model.addAttribute("paiement", paiement);
            return "admin/add_paiement"; // Thymeleaf : paiements/add.html
        }



        // Ajouter un paiement manuellement (POST form)
        @PostMapping("/add")
        public String enregistrerPaiement(@ModelAttribute Paiement paiement) {
            Contrat contrat = contratRepository.findById(paiement.getContrat().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Contrat non trouvé"));

            paiement.setContrat(contrat);
            paiementService.enregistrerPaiement(paiement);

            return "redirect:/paiements/contrat/" + contrat.getId();
        }

    }

