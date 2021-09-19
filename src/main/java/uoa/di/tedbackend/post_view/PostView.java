package uoa.di.tedbackend.post_view;

import lombok.Data;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.post_impl.PostRepository;
import uoa.di.tedbackend.user_impl.User;
import uoa.di.tedbackend.user_impl.UserRepository;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class PostView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id",referencedColumnName = "id")
    private Post post;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDate;

    public PostView() {}

    public PostView(UserRepository urepository, PostRepository prepository, int uid, int pid) {
        this.user=urepository.findById(uid).get();
        this.post=prepository.findById(pid).get();
        this.createdDate=new Date();
    }
}
