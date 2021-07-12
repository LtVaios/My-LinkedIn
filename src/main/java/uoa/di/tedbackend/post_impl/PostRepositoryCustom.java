package uoa.di.tedbackend.post_impl;

import java.util.List;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.comment_impl.Comment;

public interface PostRepositoryCustom {
    List<Post> findByUser(String username);
    List<Comment> get_comms(int post_id);
}
