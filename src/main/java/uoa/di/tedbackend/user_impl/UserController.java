package uoa.di.tedbackend.user_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
class UserController {
    BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    // Aggregate root

    @CrossOrigin(origins = "*")
    @GetMapping("/users")
    List<User> all() {
        return repository.findAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        return repository.save(newUser);
    }

    // Single item

    @CrossOrigin(origins = "*")
    @GetMapping("/users/{username}")
    User one(@PathVariable String username) {
        try {
            User test_user;
            test_user=repository.findByUsername(username);
            if(test_user!=null)
                return test_user;
            else{
                User failed_user=new User();
                failed_user.setUsername("_EMPTY_");
                return failed_user;
            }
        }
        catch(Exception e){
            throw new UserNotFoundException();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/users/getbyid/{id}")
    User one(@PathVariable int id) {
        try {
            Optional<User> test_user;
            test_user=repository.findById(id);
            if(test_user.isPresent())
                return test_user.get();
            else{
                User failed_user=new User();
                failed_user.setUsername("_EMPTY_");
                return failed_user;
            }
        }
        catch(Exception e){
            throw new UserNotFoundException();
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/users/getbyfullname/{firstname}")
    List<User> findAllUsersbyfull(@PathVariable String firstname) {
        try {
            List<User> users;
            users=repository.findByFullname(firstname);
            return users;
        }
        catch(Exception e){
            throw new UserNotFoundException();
        }
    }


    @CrossOrigin(origins = "*")
    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User user, @PathVariable int id) {
        return repository.save(user);
        /*return repository.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setPhone(newUser.getPhone());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    return repository.save(newUser);
                });*/
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/user/{id}")
    void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }

}
