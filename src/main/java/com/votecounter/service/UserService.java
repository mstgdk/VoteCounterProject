package com.votecounter.service;

import com.votecounter.domain.Role;
import com.votecounter.domain.User;
import com.votecounter.domain.enums.RoleType;
import com.votecounter.dto.request.RegisterRequest;
import com.votecounter.exception.ConflictException;
import com.votecounter.exception.message.ErrorMessage;
import com.votecounter.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.votecounter.exception.ResourceNotFoundException;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    //@Lazy PasswordEncoder: başına @Lazy ekleyerek sonsuz döngüden kurtardık.

    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }




    public User getUserByCitNumber(String citNumber){
        User user = userRepository.findByCitNumber(citNumber).orElseThrow(
                ()-> new ResourceNotFoundException(
                        String.format(ErrorMessage.USER_NOT_FOUND_EXCEPTION, citNumber)));
        return user;

    }
    public void saveUser(RegisterRequest registerRequest) {
        //!!! DTO dan gelen email sistemde daha önce var mı ???
        if(userRepository.existsByCitNumber(registerRequest.getCitNumber())){
            throw  new ConflictException(
                    String.format(ErrorMessage.CITNUMBER_ALREADY_EXIST_MESSAGE,
                            registerRequest.getCitNumber())
            );
        }

        // !!! yeni kullanıcın rol bilgisini default olarak customer atıyorum
        Role role = roleService.findByType(RoleType.ROLE_ADMIN);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        //!!! Db ye gitmeden önce şifre encode edilecek
        String encodedPassword= passwordEncoder.encode(registerRequest.getPassword());

        //!!! yeni kullanıcının gerekli bilgilerini setleyip DB ye gönderiyoruz
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setCitNumber(registerRequest.getCitNumber());
        user.setPassword(encodedPassword);

        user.setRoles(roles);

        userRepository.save(user);

    }
}
