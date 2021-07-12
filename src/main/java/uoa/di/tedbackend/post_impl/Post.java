package uoa.di.tedbackend.post_impl;

import lombok.Data;
import lombok.Generated;

import javax.persistence.*;

@Data
@Entity
public class Post {
    private @Id @GeneratedValue int id;
    private String post_body;
    private String user_id;

    public Post() {}

    public Post(String pb,String uid) {
        this.post_body=pb;
        this.user_id=uid;
    }
}
