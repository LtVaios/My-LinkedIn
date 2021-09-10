package uoa.di.tedbackend.comment_impl;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import uoa.di.tedbackend.post_impl.PostRepository;
import uoa.di.tedbackend.user_impl.UserRepository;

@RestController
@CrossOrigin(origins = "*")
class CommentController {

    private final CommentRepository repository;
    private final UserRepository urepository;
    private final PostRepository prepository;

    CommentController(CommentRepository repository,UserRepository urepository,PostRepository prepository) {
        this.repository = repository;
        this.urepository = urepository;
        this.prepository = prepository;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/comments")
    List<Comment> all() {
        return repository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/comments/ofuser/{userId}")
    List<Comment> commentsbyuser(@PathVariable (value = "userId") int userId) {
        try {
            List<Comment> comments;
            comments=repository.findCommentsByUser(userId);
            return comments;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting likes of user");
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/comments/ofpost/{postId}")
    List<Comment> commentsbypost(@PathVariable (value = "postId") int postId) {
        try {
            List<Comment> comments;
            comments=repository.findCommentsByPost(postId);
            return comments;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting likes of post");
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/comments/touser")
    List<Comment> commentstouserspost(@RequestParam int userid){
        try {
            List<Comment> comments;
            comments=repository.findCommentsToUsersPosts(userid);
            return comments;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting comments of user's posts");
        }
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/comments")
    Comment newcomm(@RequestBody Comment newComm) {
        return repository.save(newComm);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/comments/{id}")
    void deleteComment(@PathVariable int id) {
        repository.deleteById(id);
    }

}
