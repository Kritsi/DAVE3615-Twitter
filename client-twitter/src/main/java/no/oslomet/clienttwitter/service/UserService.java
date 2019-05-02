package no.oslomet.clienttwitter.service;

import no.oslomet.clienttwitter.model.Follower;
import no.oslomet.clienttwitter.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    String BASE_URL = "http://localhost:9070/users";
    private RestTemplate restTemplate = new RestTemplate();

    public List<User> getAllUsers() {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL, User[].class)
        ).collect(Collectors.toList());
    }

    public User getUserById(long id) {
        User user = restTemplate.getForObject(BASE_URL + "/" + id, User.class);
        return user;
    }

    public User getUserByUserName(String userName) {
        User user = restTemplate.getForObject(BASE_URL + "/name/" + userName, User.class);
        return user;
    }

    public List<User> getFollowersByUserId(long userId) {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL + "/followers/" + userId, User[].class)
        ).collect((Collectors.toList()));
    }

    public List<User> getFollowingByUserId(long userId) {
        return Arrays.stream(
                restTemplate.getForObject(BASE_URL + "/following/" + userId, User[].class)
        ).collect((Collectors.toList()));
    }

    public void addFollowing(long userId, long followId) {
        restTemplate.delete(BASE_URL + "/addFollowing/" + userId + "/" + followId);
    }

    public void removeFollowing(long userId, long followId) {
        restTemplate.delete(BASE_URL + "/removeFollowing/" + userId + "/" + followId);
    }

    public User saveUser(User user) {
        return restTemplate.postForObject(BASE_URL, user, User.class);
    }

    public void updateUser(long id, User user) {
        restTemplate.put(BASE_URL + "/" + id, user);
    }

    public void deleteUser(long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
