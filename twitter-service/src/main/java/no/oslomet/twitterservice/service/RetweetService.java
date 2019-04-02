package no.oslomet.twitterservice.service;

import no.oslomet.twitterservice.model.Retweet;
import no.oslomet.twitterservice.repoistory.RetweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetweetService {
    @Autowired
    private RetweetRepository retweetRepository;

    public List<Retweet> getAllBuldings() {
        return retweetRepository.findAll();
    }

    public Retweet getBuldingById(long id) {
        return retweetRepository.findById(id).get();
    }

    public Retweet saveBulding(Retweet retweet) {
        return retweetRepository.save(retweet);
    }

    public void deleteBuldingById(long id) {
        retweetRepository.deleteById(id);
    }
}
