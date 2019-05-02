package no.oslomet.clienttwitter.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private Role role;

    private List<User> following = new ArrayList<>();
    List<User> followers = new ArrayList<>();

    public User() {

    }

    public User(long id, String firstName, String lastName, String email, String userName, String password, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }
}
