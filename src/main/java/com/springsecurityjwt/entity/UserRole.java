package com.springsecurityjwt.entity;


import com.springsecurityjwt.entity.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "t_roles")
@NoArgsConstructor
@AllArgsConstructor    //TODO onceki entitylerde sorun
@Builder(toBuilder = true)
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;


}
