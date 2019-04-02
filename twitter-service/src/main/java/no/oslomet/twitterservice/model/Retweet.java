package no.oslomet.twitterservice.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Retweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;

    @Getter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;

    public Retweet() {

    }
}
