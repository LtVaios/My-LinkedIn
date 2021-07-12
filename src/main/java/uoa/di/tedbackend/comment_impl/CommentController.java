package uoa.di.tedbackend.comment_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;
import uoa.di.tedbackend.post_impl.Post;

@RestController
@CrossOrigin(origins = "*")
class CommentController {

    private final CommentRepository repository;

    CommentController(CommentRepository repository) {
        this.repository = repository;
    }

    // Aggregate root

    @CrossOrigin(origins = "*")
    @GetMapping("/comments")
    List<Comment> all() {
        return repository.findAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/comments")
    Comment newComment(@RequestBody Comment newComment) {  return repository.save(newComment); }

    // Single item

    @CrossOrigin(origins = "*")
    @GetMapping("/comments/{id}")
    Comment one(@PathVariable int id) {
        try {
            Optional<Comment> comment;
            comment=repository.findById(id);
            if(!comment.isEmpty())
                return comment.orElseThrow();
            else{
                throw new RuntimeException("Could not find a comment by this id");
            }
        }
        catch(Exception e){
            throw new RuntimeException("Error with comment getting");
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/comments/{id}")
    void deleteComment(@PathVariable int id) {
        repository.deleteById(id);
    }

}
