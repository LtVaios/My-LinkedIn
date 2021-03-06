package uoa.di.tedbackend.likes_impl;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import uoa.di.tedbackend.matrix_factorization.matrix_factorization;
import uoa.di.tedbackend.post_impl.PostRepository;
import uoa.di.tedbackend.user_impl.UserRepository;

@RestController
@CrossOrigin(origins = "*")
class LikesController {

    private final matrix_factorization mf;
    private final LikesRepository repository;
    private final UserRepository urepository;
    private final PostRepository prepository;

    LikesController(matrix_factorization mf, LikesRepository repository, UserRepository urepository, PostRepository prepository) {
        this.mf = mf;
        this.repository = repository;
        this.urepository = urepository;
        this.prepository = prepository;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/likes")
    List<Likes> allLikes() {
        return repository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/likes/ofuser/{userId}")
    List<Likes> likesbyuser(@PathVariable (value = "userId") int userId) {
        try {
            List<Likes> likes;
            likes=repository.findLikesByUser(userId);
            return likes;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting likes of user");
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/likes/ofpost/{postId}")
    List<Likes> likesbypost(@PathVariable (value = "postId") int postId) {
        try {
            List<Likes> likes;
            likes=repository.findLikesByPost(postId);
            return likes;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting likes of post");
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/likes/touser")
    List<Likes> likestouserspost(@RequestParam int userid){
        try {
            List<Likes> likes;
            likes=repository.findLikesToUsersPosts(userid);
            return likes;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting likes of post");
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/likes/addlike")
    Likes newLike(@RequestBody Likes newLike) {
        return repository.save(newLike);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/likes/{id}")
    void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }

}
