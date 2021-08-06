package uoa.di.tedbackend.comment_impl;

import lombok.Data;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.user_impl.User;

import javax.persistence.*;

@Data
@Entity
public class Comment {
    private @Id @GeneratedValue int id;
    private String comment_text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id",referencedColumnName = "id")
    private Post post;


    public Comment() {}
}
