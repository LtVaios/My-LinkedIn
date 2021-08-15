package uoa.di.tedbackend.user_impl;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import uoa.di.tedbackend.image.Image;
import uoa.di.tedbackend.job_impl.Job;

@Data
@Entity
//@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    protected boolean admin;

    private String work_experience;
    private boolean work_experience_public;

    private String education;
    private boolean education_public;

    private String skills;
    private boolean skills_public;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    @JsonIgnoreProperties("user")
    private Image img;

//    @JsonIgnoreProperties("likes")
//    @ManyToMany(fetch = FetchType.LAZY,
//            cascade = {CascadeType.MERGE},
//            mappedBy = "likes")
//    private Set<Job> likedJobs = new HashSet<>();

    public User() {}

    public User(int id,String uname,String pass, String fname, String lname, String phone, boolean admin) {
        this.id=id;
        this.username=uname;
        this.password = pass;
        this.firstName = fname;
        this.lastName = lname;
        this.phone = phone;
        this.admin = admin;
    }

}
