package com.example.test.Controller;


import com.example.test.Service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;


@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comments/create")
    public String createComment(@RequestParam Long postId, @RequestParam String content, Principal principal, HttpServletRequest request) {

        commentService.createComment(postId, principal.getName(), content);

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/posts");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comments/delete")
    public String deleteComment(@RequestParam Long id, HttpServletRequest request, Principal principal) {

        if (commentService.findById(id).getUserId().equals(principal.getName())) {
            commentService.deleteComment(id);
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/posts");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comments/like")
    public String likeComment(@RequestParam Long id, Principal principal, HttpServletRequest request) {
        commentService.likeComment(id, principal.getName());

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/posts");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comments/unlike")
    public String unlikeComment(@RequestParam Long id, Principal principal, HttpServletRequest request) {
        commentService.unlikeComment(id, principal.getName());

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/posts");
    }
}
