package no.oslomet.clienttwitter.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Hashtag {
    private long id;
    private String name;

    private List<Tweet> tweets = new ArrayList<>();

    public Hashtag() {

    }

    public Hashtag(String name) {
        this.name = name;
    }

    public Hashtag(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Hashtag(String name, List<Tweet> tweets) {
        this.name = name;
        this.tweets = tweets;
    }
}
