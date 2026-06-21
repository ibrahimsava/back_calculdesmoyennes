package com.example.tymleaf.repository;

import com.example.tymleaf.User.UsersEleves;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EleveRepository extends JpaRepository<UsersEleves,  Long> {

}
