package uoa.di.tedbackend.user_impl;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {
    private @Id String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    protected boolean admin;


    public User() {}

    public User(String uname,String pass, String fname, String lname, String phone, boolean admin) {
        this.username=uname;
        this.password = pass;
        this.firstName = fname;
        this.lastName = lname;
        this.phone = phone;
        this.admin = admin;
    }
}
