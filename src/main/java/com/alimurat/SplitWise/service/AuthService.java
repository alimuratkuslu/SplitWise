package com.alimurat.SplitWise.service;

import com.alimurat.SplitWise.dto.LoginForm;
import com.alimurat.SplitWise.dto.TokenResponseDto;
import com.alimurat.SplitWise.utils.TokenGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final TokenGenerator tokenGenerator;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserService userService, TokenGenerator tokenGenerator, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenGenerator = tokenGenerator;
        this.authenticationManager = authenticationManager;
    }

    // Gets the logged in user's email
    public String getLoggedInEmail(){
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public TokenResponseDto login(LoginForm loginForm){

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));

        return TokenResponseDto.builder()
                .accessToken(tokenGenerator.generateToken(auth))
                //.response(userService.getUserByEmail(loginForm.getEmail()))
                .build();
    }
}
