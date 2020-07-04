package com.udacity.jwdnd.course1.cloudstorage.security;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UsersService usersService;

    @Autowired
    HashService hashService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        Object password = authentication.getCredentials();
        System.err.println("username is" + username);
        Users user = usersService.findByUsername(username);
        System.err.println("found user is " + user);
        System.out.println("current password is " + password);
        UsernamePasswordAuthenticationToken token = null;
        if (user != null){
            System.out.println("saved password is " + user.getPassword());
            if (password != null){
                String encodedPassword = hashService.getHashedValue(password.toString(), user.getSalt());
                if (username.equals(user.getUsername()) && encodedPassword.equals(user.getPassword())){
                    token = new UsernamePasswordAuthenticationToken(username, password);
                } else {
                    throw new BadCredentialsException("username or password do not match");
                }
            }

        } else {
            throw new UsernameNotFoundException("user with name" + username + " not found!!");
        }
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
