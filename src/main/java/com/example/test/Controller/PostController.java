package com.example.test.Controller;

import com.example.test.Model.Comment;
import com.example.test.Model.Post;
import com.example.test.Model.PostLike;
import com.example.test.Service.CommentService;
import com.example.test.Service.PostService;
import com.example.test.util.TimeAgo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.test.Model.CommentLike;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/posts/create")
    public String createPost(@RequestParam String content, @RequestParam(required = false) MultipartFile data, Principal principal) {

        byte[] imageBytes = null;

        try {
            if (data != null && !data.isEmpty()) {
                imageBytes = data.getBytes();
            }
        } catch (Exception e) {

        } finally {

            if (imageBytes != null) {
                postService.createPost(principal.getName(), content, imageBytes);
            } else {
                postService.createPost(principal.getName(), content);
            }

            return "redirect:/posts";
        }


    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/posts/create")
    public String createPost() {
        return "createPost";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/posts")
    public String getPosts(Model model, Principal principal) {
        List<Post> posts = postService.getPosts();

        for (Post post : posts) {
            post.setTimeAgo(TimeAgo.timeAgo(post.getCreatedAt()));

            List<PostLike> likedBy = postService.getPostLikes(post.getId());
            List<Comment> comments = commentService.findByPostId(post.getId());

            for (Comment comment : comments) {
                List<CommentLike> commentLikes = commentService.getCommentLikes(comment.getId());
                comment.setLikedBy(commentLikes);
                comment.setIsLikedByMe(commentLikes.stream().anyMatch(l -> l.getUserId().equals(principal.getName())));
            }

            post.setLikedBy(likedBy);
            post.setIsLikedByMe(likedBy.stream().anyMatch(l -> l.getUserId().equals(principal.getName())));
            post.setComments(comments);
        }

        model.addAttribute("posts", posts);
        return "posts";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/posts/like")
    public String likePost(@RequestParam Long id, Principal principal, HttpServletRequest request) {
        postService.likePost(id, principal.getName());
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/posts");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/posts/unlike")
    public String unlikePost(@RequestParam Long id, Principal principal, HttpServletRequest request) {
        postService.unlikePost(id, principal.getName());
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/posts");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/posts/delete")
    public String deletePost(@RequestParam Long id, HttpServletRequest request, Principal principal) {

        if (postService.getPostById(id).getUserId().equals(principal.getName())) {
            postService.deletePost(id);
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/posts");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/posts/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, Principal principal) {

        if (!postService.getPostById(id).getUserId().equals(principal.getName())) {
            return "redirect:/posts";
        }

        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "editPost";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/posts/update")
    public String updatePost(@RequestParam Long id, @RequestParam String content, @RequestParam(required = false) MultipartFile data, Principal principal) {

        if (!postService.getPostById(id).getUserId().equals(principal.getName())) {
            return "redirect:/posts";
        }

        byte[] imageBytes = null;

        try {
            if (data != null && !data.isEmpty()) {
                imageBytes = data.getBytes();
            }
        } catch (IOException e) {

        } finally {

            if (imageBytes != null) {
                postService.updatePost(id, content, imageBytes);
            } else {
                postService.updatePost(id, content);
            }


            return "redirect:/posts";
        }
    }
}