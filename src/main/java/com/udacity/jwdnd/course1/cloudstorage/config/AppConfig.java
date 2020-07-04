package com.udacity.jwdnd.course1.cloudstorage.config;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.security.CustomAuthenticationProvider;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    @Bean
    UsersService getUsersServiceBean(){
        return new UsersService();
    }

    @Bean
    AuthenticationProvider getCustomAuthenticationProvider(){
        return new CustomAuthenticationProvider();
    }

    @Bean
    CredentialsService getCredentialsService(){
        return new CredentialsService();
    }

    @Bean
    FilesService getFilesService(){
        return new FilesService();
    }

    @Bean
    NotesService getNotesServoce(){
        return new NotesService();
    }

    @Bean
    HashService getHashingService(){
        return new HashService();
    }

    @Bean
    AuthService getAuthService(){
        return new AuthService();
    }

    @Bean
    HashService getHashService(){
        return new HashService();
    }


}
