package com.udacity.jwdnd.course1.cloudstorage.services;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UsersService {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    HashService hashService;

    public Users save(Users user){
        String salt = hashService.generateRandomSalt();
        String encodedPassword = hashService.getHashedValue(user.getPassword(), salt);
        user.setPassword(encodedPassword);
        user.setSalt(salt);
        System.err.println("user is" + user);
        usersMapper.save(user);
        Users savedUser = usersMapper.findByUsername(user.getUsername());
        if (savedUser != null){
            Credentials credentials = new Credentials();
            credentials.setPassword(encodedPassword);
            credentials.setUsername(savedUser.getUsername());
            credentials.setOwner(savedUser);
            credentialsService.save(credentials);
        }
        return savedUser;
    }

    public Users findByUsername(String username) {
        return usersMapper.findByUsername(username);
    }

    public Users findById(Integer id){
        return usersMapper.findById(id);
    }


    public void delete(Users user){
         usersMapper.delete(user);
    }


}
