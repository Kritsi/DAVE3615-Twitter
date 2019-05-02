package no.oslomet.clienttwitter.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class Retweet {
    private long id;
    private long userId;
    private Date date;
    private Tweet tweet;

    public Retweet() {

    }

    public Retweet(long userId, Tweet tweet) {
        this.userId = userId;
        this.tweet = tweet;
    }
}
