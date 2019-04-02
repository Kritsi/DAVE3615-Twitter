package no.oslomet.twitterservice.service;

import no.oslomet.twitterservice.model.Tweet;
import no.oslomet.twitterservice.repoistory.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {
    @Autowired
    private TweetRepository tweetRepository;

    public List<Tweet> getAllBuldings() {
        return tweetRepository.findAll();
    }

    public Tweet getBuldingById(long id) {
        return tweetRepository.findById(id).get();
    }

    public Tweet saveBulding(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    public void deleteBuldingById(long id) {
        tweetRepository.deleteById(id);
    }
}
