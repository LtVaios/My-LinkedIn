package uoa.di.tedbackend.comment_impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.bind.annotation.*;
import uoa.di.tedbackend.likes_impl.Likes;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.post_impl.PostRepository;
import uoa.di.tedbackend.user_impl.User;
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
