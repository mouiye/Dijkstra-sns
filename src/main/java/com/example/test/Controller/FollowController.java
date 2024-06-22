package com.example.test.Controller;


import com.example.test.Service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class FollowController {


    private final FriendService friendService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/follow")
    public String follow(@RequestParam String friendId, Principal principal) {

        friendService.addFriend(principal.getName(), friendId);

        return "redirect:/profile/" + friendId;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/unfollow")
    public String unfollow(@RequestParam String friendId, Principal principal) {

        friendService.removeFriend(principal.getName(), friendId);

        return "redirect:/profile/" + friendId;
    }
}
