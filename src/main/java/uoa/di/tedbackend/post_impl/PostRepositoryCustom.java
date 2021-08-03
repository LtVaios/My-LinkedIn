package uoa.di.tedbackend.post_impl;

import java.util.List;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.comment_impl.Comment;

public interface PostRepositoryCustom {
    List<Post> findPostsByUser(int user_id);
}
