package uoa.di.tedbackend.job_impl;

import org.springframework.web.bind.annotation.*;
import uoa.di.tedbackend.user_impl.User;
import uoa.di.tedbackend.user_impl.UserRepository;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class JobController {

    private final JobRepository repository;
    private final List<String> stopwords;

    JobController(JobRepository repository) throws IOException {
        this.repository = repository;
        File f = new File("./src/main/resources/static/stopwords.txt");
        System.out.println(f.getAbsolutePath());
        stopwords = Files.readAllLines(Paths.get(f.getAbsolutePath()));
    }

    // Aggregate root

//    @CrossOrigin(origins = "*")
//    @GetMapping("/jobs")
//    List<Job> all() {
//        return repository.findAll();
//    }
    @CrossOrigin(origins = "*")
    @GetMapping("/jobs/{id}")
    Job all(@PathVariable(value = "id") int id) {
        try {
            Optional<Job> test_job;
            test_job=repository.findById(id);
            if(test_job!=null)
                return test_job.get();
            else{
                Job failed_job=new Job();
                failed_job.setId(-1);
                return failed_job;
            }
        }
        catch(Exception e){
            throw new JobNotFoundException();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/jobs/ofuser/{userId}")
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
        search = search.replaceAll("\\s+", " "); //replace more than 1 space
        String[] allWords = search.toLowerCase().split(" ");
        for (String word: allWords){
            if (stopwords.contains(word)) continue;
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
    @DeleteMapping("/jobs/ofuser/{userId}")
    void deleteMessages(@PathVariable (value = "userId") User userid) {
        List<Job> jobs=repository.findJobByUser(userid);
        for(Job j:jobs){
            repository.deleteById(j.getId());
        }
    }
}
