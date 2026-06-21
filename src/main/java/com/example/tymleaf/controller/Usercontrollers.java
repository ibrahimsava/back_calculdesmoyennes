package com.example.tymleaf.controller;
import com.example.tymleaf.Services.userServices;
import com.example.tymleaf.User.Utilisateurs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping()
public class Usercontrollers {

    private final userServices userServices;

    @PostMapping("post")
    public Utilisateurs post(@RequestBody Utilisateurs utilisateurs) {
       return userServices.postuser(utilisateurs);
    }

    @GetMapping("get")

    public List<Utilisateurs> get(){
        return userServices.getAll();
    }
}
