package com.example.tymleaf.Services;
import com.example.tymleaf.User.Utilisateurs;
import com.example.tymleaf.repository.UtilisateursRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class userServices {
    private final UtilisateursRepository userRepo;



    public  Utilisateurs postuser(Utilisateurs user){
        return userRepo.save(user);
    }

    public List<Utilisateurs> getAll(){
        return userRepo.findAll();
    }

}
