package com.springsecurityjwt.repository;


import com.springsecurityjwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {



    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT (count(a)>0) FROM User a WHERE a.email = ?1")
    boolean countAdmin(String email);

    Optional<User> findByEmailEquals(String email);
}
