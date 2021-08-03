package uoa.di.tedbackend.post_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;
import uoa.di.tedbackend.user_impl.UserRepository;

@RestController
@CrossOrigin(origins = "*")
class PostController {

    private final PostRepository repository;
    private final UserRepository urepository;

    PostController(PostRepository repository,UserRepository urepository) {
        this.repository = repository;
        this.urepository=urepository;
    }

    // Aggregate root

    @CrossOrigin(origins = "*")
    @GetMapping("/posts")
    List<Post> all() {
        return repository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/posts/ofuser/{userId}")
    List<Post> some(@PathVariable (value = "userId") int userId) {
        try {
            List<Post> Posts;
            Posts=repository.findPostsByUser(userId);
            return Posts;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting posts of user");
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/posts/{userId}")
    Post newPost(@PathVariable (value = "userId") int userId,@RequestBody Post newPost) {
        return urepository.findById(userId).map(user -> {
            newPost.setUser(user);
            return repository.save(newPost);
        }) .orElseThrow(()->new RuntimeException("error posting post"));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/posts/{id}")
    Post one(@PathVariable int id) {
        try {
            Optional<Post> post;
            post=repository.findById(id);
            if(!post.isEmpty())
                return post.orElseThrow();
            else{
                throw new RuntimeException("Could not find a post by this id");
            }
        }
        catch(Exception e){
            throw new RuntimeException("Error with post getting");
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/posts/{id}")
    void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }

}
