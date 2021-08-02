package uoa.di.tedbackend.job_impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import uoa.di.tedbackend.user_impl.User;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    private String body;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDate;

    public Job() {
    }

}