package no.oslomet.twitterservice.repoistory;

import no.oslomet.twitterservice.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
}
