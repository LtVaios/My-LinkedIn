package uoa.di.tedbackend.matrix_factorization;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uoa.di.tedbackend.job_impl.Job;
import uoa.di.tedbackend.job_impl.JobRepository;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.post_impl.PostRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MatrixFactorizationController {
//    @Autowired
    private final matrix_factorization mf;

    private final PostRepository prepository;
    private final JobRepository jrepository;

    MatrixFactorizationController(matrix_factorization mf, PostRepository prepository, JobRepository jrepository) {
        this.mf = mf;
        this.prepository = prepository;
        this.jrepository = jrepository;
    }

    @GetMapping("/recommend/posts/{user_id}")
    List<Post> getPostsOrdered(@PathVariable int user_id){
        if (mf.post_ids==null) return new ArrayList<>();
        List<Post> posts = new ArrayList<>();
        List<Integer> post_ids = mf.post_recommendations(user_id, 50);
        for (int id:post_ids){
            posts.add(prepository.findById(id).get());
        }
        return posts;
    }

    @GetMapping("/recommend/jobs/{user_id}")
    List<Job> getJobsOrdered(@PathVariable int user_id){
        if (mf.job_ids==null) return new ArrayList<>();
        List<Job> jobs = new ArrayList<>();
        List<Integer> job_ids = mf.job_recommendations(user_id);
        for (int id:job_ids){
            jobs.add(jrepository.findById(id).get());
        }
        return jobs;
    }
}
