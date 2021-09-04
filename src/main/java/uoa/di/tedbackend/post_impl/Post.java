package uoa.di.tedbackend.post_impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import uoa.di.tedbackend.user_impl.UserRepository;
import uoa.di.tedbackend.audio_files.Audio;
import uoa.di.tedbackend.image.Image;
import uoa.di.tedbackend.user_impl.User;
import uoa.di.tedbackend.video_impl.Video;

import javax.persistence.*;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String post_body;
    private String date_time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> likes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Set<Image> images = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
//    @JsonIgnoreProperties("post")
    private Set<Video> videos = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Set<Audio> audios = new HashSet<>();

    public Post() {}

    public Post(UserRepository urepository,String pb,int uid,int id) {
        this.id=id;
        this.user=urepository.findById(uid).get();
        this.post_body=pb;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        this.date_time=formatter.format(date);
    }

}

