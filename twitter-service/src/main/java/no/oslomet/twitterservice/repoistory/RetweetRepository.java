package no.oslomet.twitterservice.repoistory;

import no.oslomet.twitterservice.model.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {
}
