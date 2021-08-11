package uoa.di.tedbackend.joblike_impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import uoa.di.tedbackend.job_impl.Job;
import uoa.di.tedbackend.user_impl.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class JobLike {

    private @Id @GeneratedValue int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id",referencedColumnName = "id")
    private Job job;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//    @SortableField
    private Date createdDate;
}
