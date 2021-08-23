package uoa.di.tedbackend.post_impl;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Generated;
import uoa.di.tedbackend.image.Image;
import uoa.di.tedbackend.user_impl.User;
import uoa.di.tedbackend.video_impl.Video;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> likes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
//    @JsonIgnoreProperties("post")
    private Set<Image> images = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
//    @JsonIgnoreProperties("post")
    private Set<Video> videos = new HashSet<>();

    public Post() {}

    public Post(String pb) {
        this.post_body=pb;
    }

//    public void addImg(Image img) {
//        this.img.add(img);
//    }
}
