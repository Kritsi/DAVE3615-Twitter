package no.oslomet.twitterservice.controller;

import no.oslomet.twitterservice.model.Tweet;
import no.oslomet.twitterservice.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    TweetService tweetService;

    @GetMapping("/")
    public String home() {
        return "This is a rest controller ";
    }

    @GetMapping("/tweets")
    public List<Tweet> getAllTweets() {
        return tweetService.getAllBuldings();
    }

    @GetMapping("/tweets/{id}")
    public Tweet getTweetById(@PathVariable long id) {
        return tweetService.getBuldingById(id);
    }

    @DeleteMapping("/tweets/{id}")
    public void deleteTweetById(@PathVariable long id) {
        tweetService.deleteBuldingById(id);
    }

    @PostMapping("/tweets")
    public Tweet createTweet(@RequestBody Tweet tweet) {
        return tweetService.saveBulding(tweet);
    }

    @PutMapping("/tweets/{id}")
    public Tweet updateTweet(@PathVariable long id, @RequestBody Tweet tweet) {
        tweet.setId(id);
        return tweetService.saveBulding(tweet);
    }
}
