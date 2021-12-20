package uoa.di.tedbackend.user_impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import uoa.di.tedbackend.image.Image;

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
    private String job_pos="unemployed";
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

    public User() {}

    public User(int id,String uname,String pass, String fname, String lname, String phone, boolean admin, String wk, String ed, String skills,String jp) {
        this.id=id;
        this.job_pos=jp;
        this.username=uname;
        this.password = pass;
        this.firstName = fname;
        this.lastName = lname;
        this.phone = phone;
        this.admin = admin;
        this.work_experience=wk;
        this.education=ed;
        this.skills=skills;
        this.work_experience_public=true;
        this.education_public=true;
        this.skills_public=true;
    }

}
