package no.oslomet.clienttwitter.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class Role {
    private long id;
    private String roleName;
    private List<User> users;

    public Role() {

    }

    public Role(long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }
}
