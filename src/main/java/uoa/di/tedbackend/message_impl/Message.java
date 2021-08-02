package uoa.di.tedbackend.message_impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Generated;
import uoa.di.tedbackend.user_impl.User;

import javax.persistence.*;

@Data
@Entity
public class Message {
    private @Id @GeneratedValue int id;
    private int sender_id;
    private int receiver_id;
    private long date_time;
    private String text;

    public Message() {}

    public Message(int sd,long dt,String text,int rd) {
        this.sender_id=sd;
        this.receiver_id=rd;
        this.date_time=dt;
        this.text=text;
    }
}
