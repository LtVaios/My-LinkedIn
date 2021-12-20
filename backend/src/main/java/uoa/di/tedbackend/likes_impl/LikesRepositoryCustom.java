package uoa.di.tedbackend.likes_impl;

import java.util.List;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.comment_impl.Comment;

public interface LikesRepositoryCustom {
    List<Likes> findLikesByPost(int postId);
    List<Likes> findLikesByUser(int userId);
    List<Likes> findLikesToUsersPosts(int userId);
}
