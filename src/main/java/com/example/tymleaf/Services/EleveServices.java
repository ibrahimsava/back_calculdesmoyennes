package com.example.tymleaf.Services;

import com.example.tymleaf.User.Notes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EleveServices {

    public Double calculerMoyenneGenerale(List<Notes> notes){
        if(notes == null || notes.isEmpty()){
            return 0.0;
        }

        // 1. On élimine uniquement les lignes qui n'ont pas de nom de matière (pour éviter le crash du regroupement)
        List<Notes> notesAvecMatiere = notes.stream()
                .filter(note -> note != null && note.getMatiere() != null)
                .collect(Collectors.toList());

        if (notesAvecMatiere.isEmpty()) {
            return 0.0;
        }

        // 2. On regroupe les notes par matière
        Map<String, List<Notes>> notesParMatiere = notesAvecMatiere.stream()
                .collect(Collectors.groupingBy(Notes::getMatiere));

        double sommeDesMoyennes = 0;

        // 3. On calcule la moyenne de chaque matière
        for (List<Notes> notesMatiere : notesParMatiere.values()) {
            double sommeNotesMatiere = 0;

            for (Notes note : notesMatiere) {
                // RÈGLE : Si la valeur est null, on considère que la note est de 0.0
                if (note.getValeur() == null) {
                    sommeNotesMatiere += 0.0;
                } else {
                    sommeNotesMatiere += note.getValeur();
                }
            }

            double moyenneMatiere = sommeNotesMatiere / notesMatiere.size();
            sommeDesMoyennes += moyenneMatiere;
        }

        // 4. Moyenne générale
        double moyenneGenerale = sommeDesMoyennes / notesParMatiere.size();

        // Arrondi à 2 décimales
        return Math.round(moyenneGenerale * 100.0) / 100.0;
    }
}
