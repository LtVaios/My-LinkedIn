package uoa.di.tedbackend.likes_impl;

import lombok.Data;
import lombok.Generated;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.user_impl.User;

import javax.persistence.*;

@Data
@Entity
public class Likes {
    private @Id @GeneratedValue int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id",referencedColumnName = "id")
    private Post post;

    public Likes() {}
}
