package uoa.di.tedbackend.application_impl;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
class ApplicationController {

    private final ApplicationRepository repository;

    ApplicationController(ApplicationRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/applications")
    List<Application> allApplications() {
        return repository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/applications/ofuser/{userId}")
    List<Application> likesbyuser(@PathVariable (value = "userId") int userId) {
        try {
            List<Application> likes;
            likes=repository.findApplicationsByUser(userId);
            return likes;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting likes of user");
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/applications/ofpost/{postId}")
    List<Application> likesbypost(@PathVariable (value = "postId") int postId) {
        try {
            List<Application> likes;
            likes=repository.findApplicationsByPost(postId);
            return likes;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting likes of job");
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/applications/touser")
    List<Application> likestousersjob(@RequestParam int userid){
        try {
            List<Application> likes;
            likes=repository.findApplicationsToUsersJobs(userid);
            return likes;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting applications of user's jobs");
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/applications")
    Application newLike(@RequestBody Application newLike) {
        return repository.save(newLike);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/applications/{id}")
    void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }

}

