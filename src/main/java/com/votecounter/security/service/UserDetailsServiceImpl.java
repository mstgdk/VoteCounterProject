package com.votecounter.security.service;

import com.votecounter.domain.User;
import com.votecounter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    // BURASI SECURİTY NİN SERVİCE KATI
    @Autowired
    private UserService userService; // BUSİNES logic için önce UserDetailsServiceImpl'den UserService'e. Sonra User Repository ye daha iyi

    @Override
    public UserDetails loadUserByUsername(String citNumber) throws UsernameNotFoundException {

        User user = userService.getUserByCitNumber(citNumber);
        return UserDetailsImpl.build(user);
    }
}
