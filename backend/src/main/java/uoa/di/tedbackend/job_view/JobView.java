package uoa.di.tedbackend.job_view;

import lombok.Data;
import uoa.di.tedbackend.job_impl.Job;
import uoa.di.tedbackend.job_impl.JobRepository;
import uoa.di.tedbackend.user_impl.User;
import uoa.di.tedbackend.user_impl.UserRepository;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class JobView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id",referencedColumnName = "id")
    private Job job;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDate;

    public JobView() {}

    public JobView(UserRepository urepository, JobRepository jrepository, int uid, int jid) {
        this.user=urepository.findById(uid).get();
        this.job=jrepository.findById(jid).get();
        this.createdDate=new Date();
    }
}
