package uoa.di.tedbackend.friends_impl;

import java.util.List;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.comment_impl.Comment;

public interface FriendsRepositoryCustom {
    List<Friends> findFriendsOfUser(int id);
    List<Friends> findFriendsAndRequestsOfUser(int id);
}
