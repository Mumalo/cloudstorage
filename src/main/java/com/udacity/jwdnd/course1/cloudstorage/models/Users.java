package com.udacity.jwdnd.course1.cloudstorage.models;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "Users")
@Setter @Getter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "username cannot be null")
    @Size(min = 5, message = "username mist contain at least 5 characters")
    @Column(unique = true)
    private String username;
    private String salt;
    @NotEmpty(message = "please enter a valid password")
    @Size(min = 5, message = "password must be at least five characters")
    private String password;
    private String firstName;
    private String lastName;
    @OneToMany
    private Set<Credentials> credentials;
    public static Users getInstance(){
        return new Users();
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", salt='" + salt + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", credentials=" + credentials +
                '}';
    }
}
