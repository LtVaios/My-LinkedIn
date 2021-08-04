package uoa.di.tedbackend.post_impl;

import java.util.*;
import lombok.Data;
import lombok.Generated;
import uoa.di.tedbackend.user_impl.User;

import javax.persistence.*;

@Data
@Entity
public class Post {
    private @Id @GeneratedValue int id;
    private String post_body;
    private String date_time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    public Post() {}

    public Post(String pb) {
        this.post_body=pb;
    }
}
