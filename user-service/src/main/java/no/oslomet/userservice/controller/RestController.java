package no.oslomet.userservice.controller;

import no.oslomet.userservice.model.User;
import no.oslomet.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable long id){
        return userService.getUserById(id);
    }

    @GetMapping("/users/name/{name}")
    public Optional<User> getUserByUserName(@PathVariable String name) {
        return userService.getUserByUserName(name);
    }

    @GetMapping("/users/followers/{id}")
    public List<User> getFollowersByUserId(@PathVariable long id) {
        User user = userService.getUserById(id);
        return user.getFollowers();
    }

    @GetMapping("/users/following/{id}")
    public List<User> getFollowingByUserId(@PathVariable long id) {
        User user = userService.getUserById(id);
        return user.getFollowing();
    }

    @RequestMapping("/users/addFollowing/{id}/{followId}")
    public void addFollowing(@PathVariable long id, @PathVariable long followId) {
        User user = userService.getUserById(id);
        User followUser = userService.getUserById(followId);

        List<User> userFollows = followUser.getFollowers();
        userFollows.add(user);
        userService.saveUser(followUser);
    }

    @RequestMapping("/users/removeFollowing/{id}/{followId}")
    public void removeFollowing(@PathVariable long id, @PathVariable long followId) {
        User user = userService.getUserById(id);
        User followUser = userService.getUserById(followId);

        List<User> userFollows = followUser.getFollowers();
        userFollows.remove(user);
        userService.saveUser(followUser);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable long id){
        userService.deleteUserById(id);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User newUser){
        userService.saveUser(newUser);
        return newUser;
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User newUser){
        newUser.setId(id);
        return userService.saveUser(newUser);
    }
}

