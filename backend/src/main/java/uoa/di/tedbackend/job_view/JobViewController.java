package uoa.di.tedbackend.job_view;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "jobviews")
class JobViewController {

    private final JobViewRepository repository;

    JobViewController(JobViewRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/")
    List<JobView> allViews() {
        return repository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/ofuser/{userId}")
    List<JobView> JobViewsbyuser(@PathVariable (value = "userId") int userId) {
        try {
            List<JobView> views;
            views = repository.findJobViewsByUser(userId);
            return views;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting likes of user");
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/ofjob/{jobId}")
    List<JobView> viewsbyjob(@PathVariable (value = "jobId") int jobId) {
        try {
            List<JobView> views;
            views=repository.findJobViewsByJob(jobId);
            return views;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting likes of job");
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/touser")
    List<JobView> viewstousersjob(@RequestParam int userid){
        try {
            List<JobView> views;
            views=repository.findJobViewsToUsersJobs(userid);
            return views;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting likes of job");
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/addview")
    JobView newView(@RequestBody JobView view) {
        return repository.save(view);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }

}
