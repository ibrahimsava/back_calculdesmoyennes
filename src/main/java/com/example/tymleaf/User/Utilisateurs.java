package com.example.tymleaf.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Utilisateurs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String adresse;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

}
