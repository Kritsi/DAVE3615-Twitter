package no.oslomet.userservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    @ManyToOne
    private Role roleId;
    @OneToMany(mappedBy = "user")
    private List<Follower> followerList;
    @OneToMany(mappedBy = "user")
    private List<Following> followingList;

}
