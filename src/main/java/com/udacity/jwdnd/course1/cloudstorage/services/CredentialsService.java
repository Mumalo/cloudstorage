package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CredentialsService {

    @Autowired
    CredentialsMapper credentialsMapper;

    @Autowired
    HashService hashService;

    @Autowired
    AuthService authService;

    public Integer save(Credentials credentials){
        String salt = hashService.generateRandomSalt();
        credentials.setKey(salt);
        credentials.setPassword(hashService.getHashedValue(credentials.getPassword(),credentials.getKey()));
        Users currentUser = authService.getCurrentUser();
        credentials.setOwner(currentUser);
        return credentialsMapper.save(credentials);
    }

    public Credentials finById(Integer id){
        return credentialsMapper.findById(id);
    }

    public Integer update(Credentials credentials){
        String key = credentials.getKey() != null ? credentials.getKey() : hashService.generateRandomSalt();
        credentials.setPassword(hashService.getHashedValue(credentials.getPassword(),key));
        return credentialsMapper.update(credentials);
    }

    public void delete(Credentials credentials){
        credentialsMapper.delete(credentials);
    }

    public List<Credentials> findAllByUser(Users user){
        return credentialsMapper.findAllByUser(user);
    }
}
