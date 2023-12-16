package com.springsecurityjwt.security.service;


import com.springsecurityjwt.entity.User;
import com.springsecurityjwt.entity.UserRole;
import com.springsecurityjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=getUserByEmail(username);
        return new UserDetailsImpl(user.getUserId(), user.getEmail(), user.getPassword(), user.getFirstName(), mapGrantedAuthority(user.getUserRoles()));
    }


    private List<SimpleGrantedAuthority> mapGrantedAuthority(Set<UserRole> roles) {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (UserRole userRole : roles) {
            authorities.add(new SimpleGrantedAuthority(userRole.getRoleType().name()));
        }
        return authorities;
    }

    private User getUserByEmail(String email) {

        return userRepository.findByEmailEquals(email).orElseThrow(
                () -> new UsernameNotFoundException("User " + email + "n't found"));
    }
}
