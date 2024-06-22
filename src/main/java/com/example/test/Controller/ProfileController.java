package com.example.test.Controller;


import com.example.test.Model.Friend;
import com.example.test.Model.Post;
import com.example.test.Model.PostLike;
import com.example.test.Service.FriendService;
import com.example.test.Service.PostService;
import com.example.test.util.TimeAgo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProfileController {

    private final FriendService friendService;
    private final PostService postService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile/{id}")
    public String profile(@PathVariable String id, Model model, Principal principal) {

        List<Friend> followers = friendService.getFollowersByUserId(id);
        List<Friend> followings = friendService.getFollowingByUserId(id);
        List<Post> posts = postService.getPostsByUserId(id);

        for (Post post : posts) {
            post.setTimeAgo(TimeAgo.timeAgo(post.getCreatedAt()));

            List<PostLike> likedBy = postService.getPostLikes(post.getId());
            post.setLikedBy(likedBy);
            post.setIsLikedByMe(likedBy.stream().anyMatch(l -> l.getUserId().equals(principal.getName())));
        }

        model.addAttribute("userId", id);
        model.addAttribute("followers", followers);
        model.addAttribute("followings", followings);
        model.addAttribute("posts", posts);


        model.addAttribute("isFollowing", followers.stream().anyMatch(f -> f.getUserId().equals(principal.getName())));

        return "profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String profile(Principal principal) {
        return "redirect:/profile/" + principal.getName();
    }
}
