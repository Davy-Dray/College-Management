package com.ragnar.MySchoolManagement.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long>{
     public boolean existsByEmail(String email);
     
    // User findByEmail(String email);
    Optional< User> findByEmail(String username);

}
