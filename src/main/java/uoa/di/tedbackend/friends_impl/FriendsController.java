package uoa.di.tedbackend.friends_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;
import uoa.di.tedbackend.post_impl.Post;

@RestController
@CrossOrigin(origins = "*")
class FriendsController {

    private final FriendsRepository repository;

    FriendsController(FriendsRepository repository) {
        this.repository = repository;
    }

    // Aggregate root

    @CrossOrigin(origins = "*")
    @GetMapping("/friends")
    List<Friends> all() {
        return repository.findAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/friends")
    Friends newFriends(@RequestBody Friends newFriends) {  return repository.save(newFriends); }

    // Single item

    @CrossOrigin(origins = "*")
    @GetMapping("/friends/{id}")
    Friends one(@PathVariable int id) {
        try {
            Optional<Friends> comment;
            comment=repository.findById(id);
            if(!comment.isEmpty())
                return comment.orElseThrow();
            else{
                throw new RuntimeException("Could not find a friend relationship by this id");
            }
        }
        catch(Exception e){
            throw new RuntimeException("Error getting friends");
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/friends/{id}")
    void deleteFriends(@PathVariable int id) {
        repository.deleteById(id);
    }

}
