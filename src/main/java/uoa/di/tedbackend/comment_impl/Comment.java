package uoa.di.tedbackend.comment_impl;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Comment {
    private @Id @GeneratedValue int id;
    private String comment_text;
    private int post_id;
    private String user_id;


    public Comment() {}

    public Comment(String ct,int pid, String uid) {
        this.comment_text=ct;
        this.post_id=pid;
        this.user_id=uid;
    }
}
