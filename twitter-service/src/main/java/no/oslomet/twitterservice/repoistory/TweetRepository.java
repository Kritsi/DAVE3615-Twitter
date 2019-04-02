package no.oslomet.twitterservice.repoistory;

import no.oslomet.twitterservice.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
