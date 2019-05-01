package no.oslomet.userservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String roleName;
    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "role")
    private List<User> users;


    public Role (String roleName){
        this.id = id;
        this.roleName = roleName;
    }
}
