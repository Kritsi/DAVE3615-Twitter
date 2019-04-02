package no.oslomet.twitterservice.service;

import no.oslomet.twitterservice.model.Hashtag;
import no.oslomet.twitterservice.repoistory.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HashtagService {
    @Autowired
    private HashtagRepository hashtagRepository;

    public List<Hashtag> getAllBuldings() {
        return hashtagRepository.findAll();
    }

    public Hashtag getBuldingById(long id) {
        return hashtagRepository.findById(id).get();
    }

    public Hashtag saveBulding(Hashtag hashtag) {
        return hashtagRepository.save(hashtag);
    }

    public void deleteBuldingById(long id) {
        hashtagRepository.deleteById(id);
    }
}
