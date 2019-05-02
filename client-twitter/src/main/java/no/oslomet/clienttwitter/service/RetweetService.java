package no.oslomet.clienttwitter.service;

import no.oslomet.clienttwitter.model.Retweet;
import no.oslomet.clienttwitter.model.Tweet;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RetweetService {
    String BASE_URL = "http://localhost:9080/retweets";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Retweet> getAllRetweets() {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL, Retweet[].class)
        ).collect(Collectors.toList());
    }

    public Retweet getRetweetById(long id) {
        Retweet retweet = restTemplate.getForObject(BASE_URL + "/" + id, Retweet.class);
        return retweet;
    }

    public Retweet getRetweetByName(String retweetName) {
        Retweet retweet = restTemplate.getForObject(BASE_URL + "/name/" + retweetName, Retweet.class);
        return retweet;
    }

    public Retweet saveRetweet(Retweet retweet) {
        return restTemplate.postForObject(BASE_URL, retweet, Retweet.class);
    }

    public List<Retweet> getAllRetweetsById(long userId) {
        List<Retweet> allRetweets = Arrays.stream(
                restTemplate.getForObject(BASE_URL, Retweet[].class)
        ).collect(Collectors.toList());
        List<Retweet> retweets = new ArrayList<>();
        for(Retweet retweet : allRetweets) {
            if(retweet.getUserId() == userId) {
                retweets.add(retweet);
            }
        }
        return retweets;
    }

    public void updateRetweet(long id, Retweet retweet) {
        restTemplate.put(BASE_URL + "/" + id, retweet);
    }

    public void deleteRetweet(long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
