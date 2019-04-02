package no.oslomet.userservice.controller;

import no.oslomet.userservice.model.Following;
import no.oslomet.userservice.service.FollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class FollowingController {

    @Autowired
    private FollowingService followingService;

    @GetMapping("/followings")
    public List<Following> getAllFollowings(){
        return followingService.getAllFollowings();
    }

    @GetMapping("/followings/{id}")
    public Following getFollowingById(@PathVariable long id){
        return followingService.getFollowingById(id);
    }

    @DeleteMapping("/followings/{id}")
    public void deleteFollowingById(@PathVariable long id){
        followingService.deleteFollowingById(id);
    }

    @PostMapping("/followings")
    public Following createFollowing(@RequestBody Following newFollowing){
        followingService.saveFollowing(newFollowing);
        return newFollowing;
    }

    @PutMapping("/followings/{id}")
    public Following updateFollowing(@PathVariable long id, @RequestBody Following newFollowing){
        newFollowing.setId(id);
        return followingService.saveFollowing(newFollowing);
    }
}
