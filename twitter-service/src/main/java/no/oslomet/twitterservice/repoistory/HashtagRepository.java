package no.oslomet.twitterservice.repoistory;

import no.oslomet.twitterservice.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findHashtagsByName(String name);
}
