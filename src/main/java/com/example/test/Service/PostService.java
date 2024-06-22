package com.example.test.Service;

import com.example.test.DAO.PostDAO;
import com.example.test.DAO.PostLikeDAO;
import com.example.test.Model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.test.Model.PostLike;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostDAO postDAO;

    private final PostLikeDAO postLikeDAO;

    public int createPost(String userId, String content, byte[] data) {
        Post post = new Post();
        post.setUserId(userId);
        post.setContent(content);
        post.setData(data);
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return postDAO.saveWithData(post);
    }

    public int createPost(String userId, String content) {
        Post post = new Post();
        post.setUserId(userId);
        post.setContent(content);
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return postDAO.saveWithoutData(post);
    }

    public int deletePost(Long id) {
        return postDAO.delete(id);
    }

    public List<Post> getPosts() {
        return postDAO.getAllPosts();
    }

    public List<Post> getPostsByUserId(String userId) {
        List<Post> posts = postDAO.findByUserId(userId);
        return posts;
    }

    public int updatePost(Long id, String content, byte[] image) {
        return postDAO.update(id, content, image);
    }

    public int updatePost(Long id, String content) {
        return postDAO.update(id, content);
    }

    public Post getPostById(Long id) {
        return postDAO.findById(id);
    }

    public List<PostLike> getPostLikes(Long postId) {
        return postLikeDAO.findByPostId(postId);
    }

    public void likePost(Long id, String userId) {
        PostLike postLike = new PostLike();
        postLike.setPostId(id);
        postLike.setUserId(userId);
        postLike.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        postLikeDAO.save(postLike);
    }

    public void unlikePost(Long id, String userId) {
        postLikeDAO.delete(id, userId);
    }

    public List<Post> findByUserId(String userId) {
        return postDAO.findByUserId(userId);
    }
}