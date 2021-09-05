package uoa.di.tedbackend.post_view;

import lombok.Data;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.user_impl.User;

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
}
