package uoa.di.tedbackend.friends_impl;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Friends {
    private @Id @GeneratedValue int id;
    private String user_one;
    private String user_two;


    public Friends() {}

    public Friends(String u1,String u2) {
        this.user_one=u1;
        this.user_two=u2;
    }
}
