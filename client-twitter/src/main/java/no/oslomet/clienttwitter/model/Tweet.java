package no.oslomet.clienttwitter.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Tweet {
    private long id;
    private Date date;
    private String text;
    private long userId;
    private boolean visible;

    List<Retweet> retweets;
    List<Hashtag> hashtags = new ArrayList<>();
    String image;

    public Tweet() {

    }

    public Tweet(long id, Date date, String text, long userId) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.userId = userId;
    }
}
