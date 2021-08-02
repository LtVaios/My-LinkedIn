package uoa.di.tedbackend.user_impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    //@GeneratedValue
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
