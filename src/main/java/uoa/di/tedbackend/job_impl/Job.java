package uoa.di.tedbackend.job_impl;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.SortableField;
import uoa.di.tedbackend.user_impl.User;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@Indexed
//@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user; //user who posts the job

    @JsonIgnoreProperties("likedJobs")
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE
    })
    @JoinTable(
            name = "job_likes",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> likes = new HashSet<>();

    @Field(name = "body")
    private String body;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//    @SortableField
    private Date createdDate;

    public Job() {
    }

}