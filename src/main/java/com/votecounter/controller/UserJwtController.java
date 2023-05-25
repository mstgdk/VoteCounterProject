package com.votecounter.controller;

import com.votecounter.dto.request.LoginRequest;
import com.votecounter.dto.request.RegisterRequest;
import com.votecounter.dto.response.LoginResponse;
import com.votecounter.dto.response.ResponseMessage;
import com.votecounter.dto.response.VtResponse;
import com.votecounter.security.jwt.JwtUtils;
import com.votecounter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserJwtController {
    // !!! Bu class'da sadece Login ve Register işlemleri yapılacak
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // !!! Register
    @PostMapping("/register")
    public ResponseEntity<VtResponse> registerUser(@Valid
                                                   @RequestBody RegisterRequest registerRequest) {
        userService.saveUser(registerRequest);

        VtResponse response = new VtResponse();
        response.setMessage(ResponseMessage.REGISTER_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // !!! Login

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid
                                                      @RequestBody LoginRequest loginRequest) {
        //  authentication Manager' a göndermek için zarfladık
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getCitNumber(),
                        loginRequest.getPassword());

        //authentication Manager --> kullanıcı valide edildi
        Authentication authentication =// görünürde yok ancak authentication Manager üzerinden security nin service katmanına gittik
                authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // !!! Kullanıcı bu aşamada valide edildi ve Token üretimine geçiliyor
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();//getPrincipal(): Anlık olarak login olan kullanıcının bilgisini bize gönderir
        String jwtToken = jwtUtils.generateJwtToken(userDetails);
        // !!! JWT token client tarafına gönderiliyor
        LoginResponse loginResponse = new LoginResponse(jwtToken);

        return new ResponseEntity<>(loginResponse,HttpStatus.OK);

    }
}
