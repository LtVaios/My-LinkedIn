package uoa.di.tedbackend.post_view;

import java.util.List;

import uoa.di.tedbackend.likes_impl.Likes;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.comment_impl.Comment;

public interface PostViewRepositoryCustom {
    List<PostView> findPostViewsByPost(int postId);
    List<PostView> findPostViewsByUser(int userId);
    List<PostView> findPostViewsToUsersPosts(int userId);
}
