package no.oslomet.clienttwitter.service;

import no.oslomet.clienttwitter.model.Retweet;
import no.oslomet.clienttwitter.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TweetService {
    String BASE_URL = "http://localhost:9080/tweets";
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    RetweetService retweetService;

    public List<Tweet> getAllTweets() {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL, Tweet[].class)
        ).collect(Collectors.toList());
    }

    public List<Tweet> getVisibleTweets(List<Tweet> tweets) {
        List<Tweet> visibleTweets = new ArrayList<>();
        for(Tweet tweet : tweets) {
            if(tweet.isVisible()) {
                visibleTweets.add(tweet);
            }
        }
        return visibleTweets;
    }

    public Tweet getTweetById(long id) {
        Tweet tweet = restTemplate.getForObject(BASE_URL + "/" + id, Tweet.class);
        return tweet;
    }

    public List<Tweet> getAllTweetsByUserId(long userId) {
        List<Retweet> retweets = retweetService.getAllRetweetsById(userId);
        List<Tweet> allTweets = Arrays.stream(
                restTemplate.getForObject(BASE_URL, Tweet[].class)
        ).collect(Collectors.toList());
        List<Tweet> tweetsByUser = new ArrayList<>();
        for(Tweet tweet : allTweets) {
            if(tweet.getUserId() == userId) {
                tweetsByUser.add(tweet);
            }
        }

        for(Retweet retweet : retweets) {
            Tweet tweet = getTweetById(retweet.getTweet().getId());
            tweet.setDate(retweet.getDate());
            tweetsByUser.add(getTweetById(retweet.getTweet().getId()));
        }

        return tweetsByUser;
    }

    public Tweet saveTweet(Tweet tweet) {
        return restTemplate.postForObject(BASE_URL, tweet, Tweet.class);
    }

    public void updateTweet(long id, Tweet tweet) {
        restTemplate.put(BASE_URL + "/" + id, tweet);
    }

    public void deleteTweet(long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
