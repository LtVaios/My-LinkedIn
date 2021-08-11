package uoa.di.tedbackend.joblike_impl;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import uoa.di.tedbackend.job_impl.JobRepository;
import uoa.di.tedbackend.user_impl.UserRepository;

@RestController
@CrossOrigin(origins = "*")
class JobLikeController {

    private final JobLikeRepository repository;

    JobLikeController(JobLikeRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/joblikes")
    List<JobLike> allJobLikes() {
        return repository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/joblikes/ofuser/{userId}")
    List<JobLike> likesbyuser(@PathVariable (value = "userId") int userId) {
        try {
            List<JobLike> likes;
            likes=repository.findJobLikesByUser(userId);
            return likes;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting likes of user");
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/joblikes/ofpost/{postId}")
    List<JobLike> likesbypost(@PathVariable (value = "postId") int postId) {
        try {
            List<JobLike> likes;
            likes=repository.findJobLikesByPost(postId);
            return likes;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting likes of job");
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/joblikes")
    JobLike newLike(@RequestBody JobLike newLike) {
        return repository.save(newLike);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/joblikes/{id}")
    void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }

}

