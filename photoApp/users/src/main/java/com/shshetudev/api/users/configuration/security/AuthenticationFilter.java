package com.shshetudev.api.users.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shshetudev.api.users.service.UsersService;
import com.shshetudev.api.users.shared.UserDto;
import com.shshetudev.api.users.ui.model.LoginRequestModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private UsersService usersService;
    private Environment environment;

    public AuthenticationFilter(UsersService usersService, Environment environment, AuthenticationManager manager) {
        this.usersService = usersService;
        this.environment = environment;
        super.setAuthenticationManager(manager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = null;
        try {
            LoginRequestModel credentials = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
            authentication = getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException("Invalid user request");
        }
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        UserDto userDetails = usersService.getUserDetailsByEmail(((User) authResult.getPrincipal()).getUsername());
        String token = Jwts.builder()
                .setSubject(userDetails.getUserId())
                .setExpiration(
                        new Date(System.currentTimeMillis() +
                        Long.parseLong(Objects.requireNonNull(environment.getProperty("token.expiration_time"))))
                ).signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();
        response.addHeader("token", token);
        response.addHeader("userId", userDetails.getUserId());
    }
}
