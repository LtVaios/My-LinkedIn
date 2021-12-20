package uoa.di.tedbackend.comment_impl;

import java.util.List;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.comment_impl.Comment;

public interface CommentRepositoryCustom {
    List<Comment> findCommentsByPost(int postId);
    List<Comment> findCommentsByUser(int userId);
    List<Comment> findCommentsToUsersPosts(int userId);
}
