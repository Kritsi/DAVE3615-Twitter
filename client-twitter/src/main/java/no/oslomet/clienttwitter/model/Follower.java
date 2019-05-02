package no.oslomet.clienttwitter.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class Follower {
    private long id;
    private User follower_id;
    private User user_id;

    public Follower() {

    }
}
