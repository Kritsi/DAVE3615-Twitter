package no.oslomet.clienttwitter.service;

import no.oslomet.clienttwitter.model.Hashtag;
import no.oslomet.clienttwitter.model.Tweet;
import no.oslomet.clienttwitter.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HashtagService {
    String BASE_URL = "http://localhost:9080/hashtags";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Hashtag> getAllHashtags() {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL, Hashtag[].class)
        ).collect(Collectors.toList());
    }

    public Hashtag getHashtagById(long id) {
        Hashtag hashtag = restTemplate.getForObject(BASE_URL + "/" + id, Hashtag.class);
        return hashtag;
    }

    public Hashtag getHashtagByName(String hashtagName) {
        Hashtag hashtag = restTemplate.getForObject(BASE_URL + "/name/" + hashtagName, Hashtag.class);
        return hashtag;
    }

    public List<Hashtag> getHashtagsFromTweet(Tweet tweet) {
        List<Hashtag> hashtagList = new ArrayList<>();

        // Hente ut hashtags
        String[] tweetArray = tweet.getText().split("\\s+");
        for(int i = 0; i < tweetArray.length; i++) {
            if(tweetArray[i].charAt(0) == '#' && tweetArray[i].length() > 1) {
                String newHashtag = tweetArray[i].substring(1);
                Hashtag hashtag = getHashtagByName(newHashtag);

                if(hashtag == null) {
                    Hashtag createHashtag = new Hashtag(newHashtag);
                    saveHashtag(createHashtag);
                    hashtag = getHashtagByName(createHashtag.getName());
                }
                hashtagList.add(hashtag);
            }
        }

        return hashtagList;
    }

    public Hashtag saveHashtag(Hashtag hashtag) {
        System.out.println("hashtag sitt navn: " + hashtag.getName());
        return restTemplate.postForObject(BASE_URL, hashtag, Hashtag.class);
    }

    public void updateHashtag(long id, Hashtag hashtag) {
        restTemplate.put(BASE_URL + "/" + id, hashtag);
    }

    public void deleteHashtag(long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
