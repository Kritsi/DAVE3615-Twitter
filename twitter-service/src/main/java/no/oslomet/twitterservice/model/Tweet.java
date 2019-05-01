package no.oslomet.twitterservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date date;
    private String text;
    private long userId;
    private String image;
    @Column(nullable = false)
    private boolean visible;

    @JsonIgnore
    @OneToMany(mappedBy = "tweet")
    List<Retweet> retweets;

    @ManyToMany
    @JoinTable(name="tweetHashtags",
            joinColumns = { @JoinColumn(name = "tweet_id") },
            inverseJoinColumns = { @JoinColumn(name = "hashtag_id")})
    List<Hashtag> hashtags = new ArrayList<>();


    public Tweet() {

    }
}
