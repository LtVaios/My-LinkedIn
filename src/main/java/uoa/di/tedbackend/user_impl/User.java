package uoa.di.tedbackend.user_impl;

import lombok.Data;
import org.springframework.data.util.Pair;
import org.w3c.dom.Text;

import javax.persistence.*;
import javax.swing.text.StyledEditorKit;
import java.awt.*;

@Data
@Entity
public class User {
    private @Id String username;
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

    public User(String uname, String pass, String fname, String lname, String phone, boolean admin) {
        this.username=uname;
        this.password = pass;
        this.firstName = fname;
        this.lastName = lname;
        this.phone = phone;
        this.admin = admin;
    }
}
