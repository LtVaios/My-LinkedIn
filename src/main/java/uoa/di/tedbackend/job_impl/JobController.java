package uoa.di.tedbackend.job_impl;

import org.springframework.web.bind.annotation.*;
import uoa.di.tedbackend.job_impl.Job;
import uoa.di.tedbackend.job_impl.JobRepository;
import uoa.di.tedbackend.user_impl.User;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class JobController {

    private final JobRepository repository;

    JobController(JobRepository repository) {
        this.repository = repository;
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
    @DeleteMapping("/jobs/{userId}")
    void deleteMessages(@PathVariable (value = "userId") User userid) {
        List<Job> jobs=repository.findJobByUserOrderById(userid);
        for(Job j:jobs){
            repository.deleteById(j.getId());
        }
    }
}
