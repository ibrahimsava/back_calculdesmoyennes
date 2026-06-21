package com.example.tymleaf.controller;
import com.example.tymleaf.Services.EleveServices;
import com.example.tymleaf.User.Notes;
import com.example.tymleaf.User.UsersEleves;
import com.example.tymleaf.repository.EleveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/eleves")
@RequiredArgsConstructor
public class EleveController {

  final private EleveRepository eleveRepository;
  final private EleveServices eleveServices;


    @GetMapping("get")
    public List<UsersEleves> getAllEleves() {
        List<UsersEleves> Eleves= eleveRepository.findAll();

        for (UsersEleves usersEleves : Eleves) {

            if (usersEleves.getNote() == null) {
                usersEleves.setNote(new ArrayList<>());
            }
            // Calcul de la moyenne sans risque de plantage
            Double moyenne = eleveServices
                    .calculerMoyenneGenerale(usersEleves.getNote());
            usersEleves.setMoyenneGenerale(moyenne);
        }
        return Eleves;
    }

    @PostMapping()
    public UsersEleves saveUserEleves(@RequestBody UsersEleves usersEleves) {
        UsersEleves usersEleves1 = eleveRepository.save(usersEleves);
        usersEleves1.setMoyenneGenerale(0.0);
        return usersEleves1;
    }

    @PostMapping("/{id}/notes")
    public UsersEleves ajouterNoteEleve(@PathVariable Long id, @RequestBody Notes notes) {
        UsersEleves usersElves=eleveRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Élève non trouvé"));

        usersElves.getNote().add(notes);

        UsersEleves usersElevesMiseajour = eleveRepository.save(usersElves);

        usersElevesMiseajour.setMoyenneGenerale(eleveServices.calculerMoyenneGenerale(usersElevesMiseajour.getNote()));

        return usersElevesMiseajour;

    }


    // --- NOUVELLE MÉTHODE UPDATE ---
    @PutMapping("/{id}")
    public UsersEleves updateEleve(@PathVariable Long id, @RequestBody UsersEleves detailsEleve) {
        // 1. Recherche de l'élève existant dans la base de données
        UsersEleves eleveExistant = eleveRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Élève non trouvé avec l'id : " + id));

        // 2. Mise à jour des informations de base
        eleveExistant.setNom(detailsEleve.getNom());
        eleveExistant.setPrenom(detailsEleve.getPrenom());

        // 3. Sauvegarde en base de données
        UsersEleves eleveModifie = eleveRepository.save(eleveExistant);

        // 4. Sécurité et calcul de sa moyenne avant retour JSON
        if (eleveModifie.getNote() == null) {
            eleveModifie.setNote(new ArrayList<>());
        }
        eleveModifie.setMoyenneGenerale(eleveServices.calculerMoyenneGenerale(eleveModifie.getNote()));

        return eleveModifie;




    }


    // --- MÉTHODE POUR MODIFIER UNE NOTE SPÉCIFIQUE (PAR SON INDEX) ---
    @PutMapping("/{id}/notes/{index}")
    public UsersEleves updateNoteEleve(
            @PathVariable Long id,
            @PathVariable int index,
            @RequestBody Notes nouvellesInfosNote) {

        // 1. On cherche l'élève
        UsersEleves usersElves = eleveRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Élève non trouvé"));

        List<Notes> listeNotes = usersElves.getNote();

        // 2. Sécurité : On vérifie si l'index demandé existe bien dans sa liste
        if (listeNotes == null || index < 0 || index >= listeNotes.size()) {
            throw new RuntimeException("La note à l'index " + index + " n'existe pas pour cet élève.");
        }

        // 3. On récupère la note existante à cet index et on applique les modifications
        Notes noteAModifier = listeNotes.get(index);
        noteAModifier.setMatiere(nouvellesInfosNote.getMatiere());
        noteAModifier.setValeur(nouvellesInfosNote.getValeur());

        // 4. On sauvegarde l'élève avec la liste mise à jour
        UsersEleves eleveMisAJour = eleveRepository.save(usersElves);

        // 5. On recalcule la moyenne générale avant le retour JSON
        eleveMisAJour.setMoyenneGenerale(eleveServices.calculerMoyenneGenerale(eleveMisAJour.getNote()));

        return eleveMisAJour;
    }






}
