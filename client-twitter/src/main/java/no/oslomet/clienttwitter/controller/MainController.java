package no.oslomet.clienttwitter.controller;

import no.oslomet.clienttwitter.model.*;
import no.oslomet.clienttwitter.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    TweetService tweetService;

    @Autowired
    HashtagService hashtagService;

    @Autowired
    RetweetService retweetService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping({"/", "/login"})
    public String home() {
        return "login";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User me = userService.getUserByUserName(auth.getName());
        List<User> myFollowingList = userService.getFollowingByUserId(me.getId());
        List<Tweet> tweetsFromFollowingList = new ArrayList<>();
        for(User user : myFollowingList) {
            List<Tweet> tweetList = tweetService.getAllTweetsByUserId(user.getId());
            for(Tweet tweet : tweetList) {
                tweetsFromFollowingList.add(tweet);
            }
        }
        List<Tweet> visibleTweets = tweetService.getVisibleTweets(tweetsFromFollowingList);
        visibleTweets.sort(Comparator.comparing(o -> o.getDate()));
        Collections.reverse(visibleTweets);

        model.addAttribute("me", me);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("tweets", visibleTweets);
        model.addAttribute("tweet", new Tweet());
        return "index";
    }

    @GetMapping("/showProfile/{id}")
    public String showProfile(@PathVariable long id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User me = userService.getUserByUserName(auth.getName());
        if (me != null) {
            model.addAttribute("me", me);
        }
        User user = userService.getUserById(id);
        List<User> followerList = userService.getFollowersByUserId(user.getId());
        List<User> followingList = userService.getFollowingByUserId(user.getId());
        List<Tweet> tweetList = tweetService.getAllTweetsByUserId(user.getId());
        List<Tweet> visibleTweets = tweetService.getVisibleTweets(tweetList);
        visibleTweets.sort(Comparator.comparing(o -> o.getDate()));
        Collections.reverse(visibleTweets);

        List<User> meFollows = userService.getFollowingByUserId(me.getId());
        Boolean following = false;
        for(User follow : meFollows) {
            if(follow.getId() == user.getId()) {
                following = true;
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("followerNbr", followerList.size());
        model.addAttribute("tweets", visibleTweets);
        model.addAttribute("followingNbr", followingList.size());
        model.addAttribute("tweet", new Tweet());
        model.addAttribute("follow", following);

        return "profile";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "signup";
    }

    @PostMapping("/processRegistration")
    public String register(@ModelAttribute("user") User user, @RequestParam("roleId") String idRole) {
        user.setRole(roleService.getRoleById(Integer.parseInt(idRole)));
        userService.saveUser(user);
        return "redirect:/";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        User me = userService.getUserById(user.getId());
        user.setRole(me.getRole());
        if(user.getPassword().equals("")) {
            User originalUser = userService.getUserById(user.getId());
            user.setPassword(originalUser.getPassword());
        }
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/editUser")
    public String updateUser(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/home";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }

    @PostMapping("/saveTweet")
    public String saveTweet(@ModelAttribute("Tweet") Tweet tweet, Model model,
                            @RequestParam("imageFile") MultipartFile imageFile) {
        String path = "C:\\Users\\krist\\IdeaProjects\\client-twitter\\src\\main\\resources\\static\\images\\" + imageFile.getOriginalFilename();
        try {
            imageFile.transferTo(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tweet.setImage(imageFile.getOriginalFilename());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUserName(auth.getName());
        tweet.setDate(new Date());
        tweet.setUserId(user.getId());
        tweet.setVisible(true);
        List<Hashtag> hashtagList = hashtagService.getHashtagsFromTweet(tweet);
        tweet.setHashtags(hashtagList);
        tweetService.saveTweet(tweet);
        return "redirect:/myProfile";
    }

    @GetMapping("/followers/{id}")
    public String followers(@PathVariable long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User me = userService.getUserByUserName(auth.getName());
        User user = userService.getUserById(id);
        List<User> followers = userService.getFollowersByUserId(user.getId());

        model.addAttribute("me", me);
        model.addAttribute("user", user);
        model.addAttribute("followers", followers);
        model.addAttribute("list", "followers");
        return "followers";
    }

    @GetMapping("/following/{id}")
    public String following(@PathVariable long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User me = userService.getUserByUserName(auth.getName());
        User user = userService.getUserById(id);
        List<User> following = userService.getFollowingByUserId(id);

        model.addAttribute("me", me);
        model.addAttribute("user", user);
        model.addAttribute("followers", following);
        model.addAttribute("list", "following");
        return "followers";
    }

    @GetMapping("/retweetTweet/{id}")
    public String retweetTweet(@PathVariable long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User me = userService.getUserByUserName(auth.getName());
        Tweet tweet = tweetService.getTweetById(id);
        Retweet retweet = new Retweet(me.getId(), tweet);
        retweet.setDate(new Date());
        retweetService.saveRetweet(retweet);
        return "redirect:/home";
    }

    @GetMapping("/showAllTweets")
    public String showAllTweets(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User me = userService.getUserByUserName(auth.getName());
        List<Tweet> allTweets = tweetService.getAllTweets();
        List<Tweet> visibleTweets = tweetService.getVisibleTweets(allTweets);
        visibleTweets.sort(Comparator.comparing(o -> o.getDate()));
        Collections.reverse(visibleTweets);

        model.addAttribute("me", me);
        model.addAttribute("tweets", visibleTweets);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("tweet", new Tweet());
        return "showTweets";
    }

    @GetMapping("/myProfile")
    public String myProfile(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUserName(auth.getName());
        List<User> followerList = userService.getFollowersByUserId(user.getId());
        List<User> followingList = userService.getFollowingByUserId(user.getId());
        List<Tweet> tweetList = tweetService.getAllTweetsByUserId(user.getId());
        tweetList.sort(Comparator.comparing(o -> o.getDate()));
        Collections.reverse(tweetList);

        model.addAttribute("me", user);
        model.addAttribute("user", user);
        model.addAttribute("followerNbr", followerList.size());
        model.addAttribute("tweets", tweetList);
        model.addAttribute("followingNbr", followingList.size());
        model.addAttribute("tweet", new Tweet());
        model.addAttribute("follow", false);
        return "profile";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User me = userService.getUserByUserName(auth.getName());

        model.addAttribute("me", me);
        model.addAttribute("user", me);

        return "settings";
    }

    @GetMapping("/editUserAdmin/{id}")
    public String editUserAdmin(@PathVariable long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User me = userService.getUserByUserName(auth.getName());

        model.addAttribute("me", me);
        model.addAttribute("user", userService.getUserById(id));
        return "settings";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User me = userService.getUserByUserName(auth.getName());

        model.addAttribute("me", me);
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @RequestMapping("/tweetMatch")
    public String bookMatch(@RequestParam("searchInput") String searchInput, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User me = userService.getUserByUserName(auth.getName());
        List<Tweet> allTweets = tweetService.getAllTweets();
        Pattern pattern = Pattern.compile(searchInput, Pattern.CASE_INSENSITIVE);
        List<Tweet> tweetMatch = new ArrayList<>();
        for (Tweet tweet : allTweets) {
            Matcher m = pattern.matcher(tweet.getText());
            if (m.find()) {
                tweetMatch.add(tweet);
            }
        }
        List<Tweet> visibleTweet = tweetService.getVisibleTweets(tweetMatch);

        model.addAttribute("me", me);
        model.addAttribute("tweets", visibleTweet);
        model.addAttribute("tweet", new Tweet());

        return "index";
    }

    @GetMapping("follow/{id}")
    public String follow(@PathVariable long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User me = userService.getUserByUserName(auth.getName());
        User followUser = userService.getUserById(id);
        userService.addFollowing(me.getId(), followUser.getId());

        model.addAttribute("me", me);
        model.addAttribute("id", followUser.getId());

        return "redirect:/showProfile/{id}";
    }

    @GetMapping("removeFollow/{id}")
    public String removeFollow(@PathVariable long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User me = userService.getUserByUserName(auth.getName());
        User followUser = userService.getUserById(id);
        userService.removeFollowing(me.getId(), followUser.getId());

        model.addAttribute("me", me);
        model.addAttribute("id", followUser.getId());

        return "redirect:/showProfile/{id}";
    }

    @GetMapping("makePrivate/{id}")
    public String makePrivate(@PathVariable long id) {
        Tweet tweet = tweetService.getTweetById(id);
        tweet.setVisible(false);
        tweetService.saveTweet(tweet);

        return "redirect:/myProfile";
    }

    @GetMapping("makePublic/{id}")
    public String makePublic(@PathVariable long id) {
        Tweet tweet = tweetService.getTweetById(id);
        tweet.setVisible(true);
        tweetService.saveTweet(tweet);

        return "redirect:/myProfile";
    }
}
