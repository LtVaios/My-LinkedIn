package uoa.di.tedbackend.friends_impl;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Friends {
    private @Id @GeneratedValue int id;
    private int user_one;
    private int user_two;
    private String state;


    public Friends() {}

    public Friends(int id,int u1,int u2,String state) {
        this.state="completed";
        this.user_one=u1;
        this.user_two=u2;
    }
}
