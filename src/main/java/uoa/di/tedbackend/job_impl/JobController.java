package uoa.di.tedbackend.job_impl;

import org.springframework.web.bind.annotation.*;
import uoa.di.tedbackend.job_impl.Job;
import uoa.di.tedbackend.job_impl.JobRepository;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.user_impl.User;
import uoa.di.tedbackend.user_impl.UserRepository;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

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

//    @CrossOrigin(origins = "*")
//    @GetMapping("/jobs")
//    List<Job> all() {
//        return repository.findAll();
//    }

    @CrossOrigin(origins = "*")
    @GetMapping("/jobs/{userId}")
    List<Job> all(@PathVariable(value = "userId") User userid) {
        return repository.findJobByUser(userid);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/jobs")
    List<Job> all(@RequestParam(value = "search", required = false) String search) throws UnsupportedEncodingException {
        if (search == null) return repository.findAll();
//        System.out.println("In get by skills after null check: "+search);
        Set<Job> jobs = new HashSet<Job>();
        search = URLDecoder.decode(search, StandardCharsets.UTF_8.toString());
//        System.out.println("In get by skills after decode: "+search);
        search = search.replaceAll("[,.!?\\-]", " ");
        search = search.replaceAll("\\s+", " ");
        String[] allWords = search.toLowerCase().split(" ");
        for (String word: allWords){
//            System.out.println("print word: "+ word);
            List<Job> temp = repository.getJobBasedOnWord(word);
            if (!temp.isEmpty())
                jobs.addAll(temp);
        }
        return new ArrayList<Job>(jobs);
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
        List<Job> jobs=repository.findJobByUser(userid);
        for(Job j:jobs){
            repository.deleteById(j.getId());
        }
    }
}
