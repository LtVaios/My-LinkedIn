package uoa.di.tedbackend.friends_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;


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
    @GetMapping("/friends/{user_one}")
    List<Friends> one(@PathVariable int user_one) {
        try {
            return this.repository.findFriendsOfUser(user_one);
        }
        catch(Exception e){
            throw new RuntimeException();
        }
    }


    @CrossOrigin(origins = "*")
    @DeleteMapping("/friends/{id}")
    void deleteFriends(@PathVariable int id) {
        repository.deleteById(id);
    }

}
