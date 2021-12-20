package uoa.di.tedbackend.job_impl;

import lombok.Data;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import uoa.di.tedbackend.user_impl.User;
import uoa.di.tedbackend.user_impl.UserRepository;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@Indexed
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user; //user who posts the job

    @Field(name = "body")
    private String body;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//    @SortableField
    private Date createdDate;

    public Job() { }

    public Job(UserRepository urepository, String b, int uid, int id) {
        this.id=id;
        this.user=urepository.findById(uid).get();
        this.body=b;
        this.createdDate=new Date();
    }

}