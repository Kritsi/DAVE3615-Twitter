package no.oslomet.twitterservice.service;

import no.oslomet.twitterservice.model.Hashtag;
import no.oslomet.twitterservice.repoistory.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Hashtag> findHashtagByName(String name) {
        return hashtagRepository.findHashtagsByName(name);
    }

    public Hashtag saveBulding(Hashtag hashtag) {
        return hashtagRepository.save(hashtag);
    }

    public void deleteBuldingById(long id) {
        hashtagRepository.deleteById(id);
    }
}
