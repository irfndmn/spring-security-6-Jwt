package com.springsecurityjwt.repository;



import com.springsecurityjwt.entity.UserRole;
import com.springsecurityjwt.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {


    @Query("SELECT r FROM UserRole r WHERE r.roleType = ?1")
    Optional<UserRole> findByEnumRoleEquals(RoleType roleType);

    @Query("SELECT (count (r)>0) FROM UserRole r WHERE r.roleType =?1")
    boolean existsByEnumRoleEquals(RoleType roleType);

//    @Query(value = "SELECT (count (r)>0) FROM roles r WHERE r.role_type =?1",nativeQuery = true)
//    boolean existsByEnumRoleEquals(RoleType roleType);
}
