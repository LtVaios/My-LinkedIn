package uoa.di.tedbackend.post_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
class PostController {

    private final PostRepository repository;

    PostController(PostRepository repository) {
        this.repository = repository;
    }

    // Aggregate root

    @CrossOrigin(origins = "*")
    @GetMapping("/posts")
    List<Post> all() {
        return repository.findAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/posts")
    Post newUser(@RequestBody Post newPost) {  return repository.save(newPost); }

    // Single item

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
    @PutMapping("/posts/{id}")
    Post replaceUser(@RequestBody Post newPost, @PathVariable int id) {

        return repository.findById(id)
                .map(Post -> {
                    Post.setUser_id(newPost.getUser_id());
                    Post.setPost_body(newPost.getPost_body());
                    return repository.save(Post);
                })
                .orElseGet(() -> {
                    newPost.setId(id);
                    return repository.save(newPost);
                });
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/posts/{id}")
    void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }

}
