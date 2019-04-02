package no.oslomet.twitterservice.controller;

import no.oslomet.twitterservice.model.Retweet;
import no.oslomet.twitterservice.service.RetweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RetweetController {
    @Autowired
    RetweetService retweetService;

    @GetMapping("/retweets")
    public List<Retweet> getAllRetweets() {
        return retweetService.getAllBuldings();
    }

    @GetMapping("/retweets/{id}")
    public Retweet getRetweetById(@PathVariable long id) {
        return retweetService.getBuldingById(id);
    }

    @DeleteMapping("/retweets/{id}")
    public void deleteRetweetById(@PathVariable long id) {
        retweetService.deleteBuldingById(id);
    }

    @PostMapping("/retweets")
    public Retweet createRetweet(@RequestBody Retweet retweet) {
        return retweetService.saveBulding(retweet);
    }

    @PutMapping("/retweets/{id}")
    public Retweet updateRetweet(@PathVariable long id, @RequestBody Retweet retweet) {
        retweet.setId(id);
        return retweetService.saveBulding(retweet);
    }
}
