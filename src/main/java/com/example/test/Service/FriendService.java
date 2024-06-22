package com.example.test.Service;

import com.example.test.DAO.FriendDAO;
import com.example.test.Model.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {
    @Autowired
    private FriendDAO friendDAO;

    public int addFriend(String userId, String friendId) {
        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendId(friendId);
        return friendDAO.save(friend);
    }

    public int removeFriend(String userId, String friendId) {
        return friendDAO.delete(userId, friendId);
    }

    public List<Friend> getFollowersByUserId(String userId) {
        return friendDAO.getFollowersByUserId(userId);
    }

    public List<Friend> getFollowingByUserId(String userId) {
        return friendDAO.getFollowingByUserId(userId);
    }
}