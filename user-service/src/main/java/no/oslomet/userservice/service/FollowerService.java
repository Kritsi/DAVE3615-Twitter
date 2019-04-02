package no.oslomet.userservice.service;

import no.oslomet.userservice.model.Follower;
import no.oslomet.userservice.repository.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowerService {

    @Autowired
    private FollowerRepository followerRepository;

    public List<Follower> getAllFollowers() {
        return followerRepository.findAll();
    }

    public Follower getFollowerById(long id){
        return followerRepository.findById(id).get();
    }

    public Follower saveFollower(Follower newFollower){
        return followerRepository.save(newFollower);
    }

    public void deleteFollowerById(long id){
        followerRepository.deleteById(id);
    }
}
