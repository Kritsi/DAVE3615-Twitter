package no.oslomet.twitterservice.controller;

import no.oslomet.twitterservice.model.Hashtag;
import no.oslomet.twitterservice.service.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class HashtagController {
    @Autowired
    HashtagService hashtagService;

    @GetMapping("/hashtags")
    public List<Hashtag> getAllHashtags() {
        return hashtagService.getAllBuldings();
    }

    @GetMapping("/hashtags/{id}")
    public Hashtag getHashtagById(@PathVariable long id) {
        return hashtagService.getBuldingById(id);
    }

    @DeleteMapping("/hashtags/{id}")
    public void deleteHashtagById(@PathVariable long id) {
        hashtagService.deleteBuldingById(id);
    }

    @PostMapping("/hashtags")
    public Hashtag createHashtag(@RequestBody Hashtag hashtag) {
        return hashtagService.saveBulding(hashtag);
    }

    @PutMapping("/hashtags/{id}")
    public Hashtag updateHashtag(@PathVariable long id, @RequestBody Hashtag hashtag) {
        hashtag.setId(id);
        return hashtagService.saveBulding(hashtag);
    }
}
