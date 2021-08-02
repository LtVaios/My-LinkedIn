package uoa.di.tedbackend.message_impl;

import java.util.List;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.comment_impl.Comment;

public interface MessageRepositoryCustom {
    List<Message> findMessagesByUser(int id);
}
