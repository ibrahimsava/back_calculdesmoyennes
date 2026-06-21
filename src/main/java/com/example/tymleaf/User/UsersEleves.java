package com.example.tymleaf.User;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersEleves {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name ="notes_eleves",
            joinColumns =
            @JoinColumn(name =
                    "eleve_id"))

    private List<Notes> note=new ArrayList<>();

    @Transient
    private Double moyenneGenerale;


}
