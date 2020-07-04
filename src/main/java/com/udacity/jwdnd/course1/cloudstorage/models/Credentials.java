package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Credentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @URL(message = "please enter a valid url")
    private String url;
    private String username;
    private String password;
    private String key;
    @ManyToOne
    @JoinColumn(name = "userid")
    private Users owner;

    @Override
    public String toString() {
        return "Credentials{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", key='" + key + '\'' +
                ", owner=" + owner +
                '}';
    }
}
