package no.oslomet.userservice.service;

import no.oslomet.userservice.model.Following;
import no.oslomet.userservice.repository.FollowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowingService {
    @Autowired
    private FollowingRepository followingRepository;

    public List<Following> getAllFollowings() {
        return followingRepository.findAll();
    }

    public Following getFollowingById(long id){
        return followingRepository.findById(id).get();
    }

    public Following saveFollowing(Following newFollowing){
        return followingRepository.save(newFollowing);
    }

    public void deleteFollowingById(long id){
        followingRepository.deleteById(id);
    }
}
