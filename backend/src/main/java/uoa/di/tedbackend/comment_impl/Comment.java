package uoa.di.tedbackend.comment_impl;

import lombok.Data;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.post_impl.PostRepository;
import uoa.di.tedbackend.user_impl.User;
import uoa.di.tedbackend.user_impl.UserRepository;

import javax.persistence.*;
import java.util.Date;

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

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//    @SortableField
    private Date createdDate;

    public Comment() {}

    public Comment(PostRepository prepo, UserRepository urepo, String ct, int uid, int pid) {
        this.createdDate=new Date();
        this.user=urepo.findById(uid).get();
        this.post=prepo.findById(pid).get();
        this.comment_text=ct;
    }
}
