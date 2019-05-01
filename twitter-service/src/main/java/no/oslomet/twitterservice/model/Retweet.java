package no.oslomet.twitterservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Retweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;

    public Retweet() {

    }
}
