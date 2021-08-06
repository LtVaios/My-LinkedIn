package uoa.di.tedbackend.job_impl;

import org.springframework.web.bind.annotation.*;
import uoa.di.tedbackend.job_impl.Job;
import uoa.di.tedbackend.job_impl.JobRepository;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.user_impl.User;
import uoa.di.tedbackend.user_impl.UserRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class JobController {

    private final JobRepository repository;
    private final UserRepository urepository;

    JobController(JobRepository repository, UserRepository urepository) {
        this.repository = repository;
        this.urepository = urepository;
    }

    // Aggregate root

    @CrossOrigin(origins = "*")
    @GetMapping("/jobs")
    List<Job> all() {
        return repository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/jobs/{userId}")
    List<Job> all(@PathVariable(value = "userId") User userid) {
        return repository.findJobByUserOrderById(userid);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/jobs")
    Job newMessage(@RequestBody Job newJob) {
        return repository.save(newJob);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/jobs/like")
    Job newLike(@RequestParam(value="jobid") int jobid, @RequestParam(value="userid") int userid) {
        System.out.println("in new like");
        return repository.findById(jobid).map(job -> urepository.findById(userid).map(user -> {
            System.out.println("in new like2");
            job.getLikes().add(user);
            System.out.println("in new like3"+job);
//            user.getLikedJobs().add(job);
            System.out.println("in new like4");
//                System.out.println("in new like5");
            repository.save(job);
            System.out.println("in new like6");
            return job;
        }) .orElseThrow(()->new RuntimeException("error in like1"))) .orElseThrow(()->new RuntimeException("error in like2"));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/jobs/{userId}")
    void deleteMessages(@PathVariable (value = "userId") User userid) {
        List<Job> jobs=repository.findJobByUserOrderById(userid);
        for(Job j:jobs){
            repository.deleteById(j.getId());
        }
    }
}
