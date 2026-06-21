package com.example.tymleaf.User;


import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Notes {

    private String matiere;
    private Double valeur;

}
