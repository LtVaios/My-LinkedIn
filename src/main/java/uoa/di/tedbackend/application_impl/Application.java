package uoa.di.tedbackend.application_impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import uoa.di.tedbackend.job_impl.Job;
import uoa.di.tedbackend.job_impl.JobRepository;
import uoa.di.tedbackend.user_impl.User;
import uoa.di.tedbackend.user_impl.UserRepository;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Application {

    private @Id @GeneratedValue int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id",referencedColumnName = "id")
    private Job job;

    private String text;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//    @SortableField
    private Date createdDate;

    public Application(UserRepository urepository, JobRepository jrepository, int uid, int jid, String text) {
        this.user=urepository.findById(uid).get();
        this.job=jrepository.findById(jid).get();
        this.createdDate=new Date();
        this.text = text;
    }
}
