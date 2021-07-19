package uoa.di.tedbackend.user_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
class UserController {

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
    User newUser(@RequestBody User newUser) {  return repository.save(newUser); }

    // Single item

    @CrossOrigin(origins = "*")
    @GetMapping("/users/{id}")
    User one(@PathVariable String id) {
        try {
            Optional<User> test_user;
            test_user=repository.findById(id);
            if(!test_user.isEmpty())
                return test_user.orElseThrow();
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
    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newData, @PathVariable String id) {
        return repository.save(newData);
//        String[] splitted;
//        splitted = newData.split(",");
//        if (splitted[0].equals("pass")) {
//            return repository.findById(id)
//                    .map(user -> {
//                        user.setPassword(splitted[1]);
//                        return repository.save(user);
//                    })
//                    .orElseGet(() -> {
//                        User newuser = new User();
//                        newuser.setUsername("empty");
//                        return newuser;
//                    });
//        } else if (splitted[0].equals("email")){
//            return repository.findById(id)
//                    .map(user -> {
//                        user.setFirstName(splitted[1]); //TODO this changes first name because we can't change id
//                        return repository.save(user);
//                    })
//                    .orElseGet(() -> {
//                        User newuser = new User();
//                        newuser.setUsername("empty");
//                        return newuser;
//                    });
//        } else {
//            return repository.findById(id)
//                    .map(user -> {
//                        user.
//                    })
//                    .orElseGet(() -> {
//                        User newuser = new User();
//                        newuser.setUsername("empty");
//                        return newuser;
//                    });
//        }
    }

//    @CrossOrigin(origins = "*")
//    @GetMapping("/users/{id}/{field}")
//    User getInfo(@RequestBody String newData, @PathVariable String id, @PathVariable String field) {
//        if (field.equals("workexp")) {
//            return repository.findById(id)
//                    .map(user -> {
//                        user.setWork_experience(newData);
//                        return repository.save(user);
//                    })
//                    .orElseGet(() -> {
//                        User newuser = new User();
//                        newuser.setUsername("empty");
//                        return newuser;
//                    });
//        } else if (field.equals("education")) {
//            return repository.findById(id)
//                    .map(user -> {
//                        user.setEducation(newData);
//                        return repository.save(user);
//                    })
//                    .orElseGet(() -> {
//                        User newuser = new User();
//                        newuser.setUsername("empty");
//                        return newuser;
//                    });
//        } else {
//            return repository.findById(id)
//                    .map(user -> {
//                        user.setSkills(newData);
//                        return repository.save(user);
//                    })
//                    .orElseGet(() -> {
//                        User newuser = new User();
//                        newuser.setUsername("empty");
//                        return newuser;
//                    });
//        }
//
//    }

    @CrossOrigin(origins = "*")
    @PutMapping("/users/{id}/{field}")
    User replaceInfo(@RequestBody String newData, @PathVariable String id, @PathVariable String field) {
        if (field.equals("workexp")) {
            return repository.findById(id)
                .map(user -> {
                    user.setWork_experience(newData);
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    User newuser = new User();
                    newuser.setUsername("empty");
                    return newuser;
                });
        } else if (field.equals("education")) {
            return repository.findById(id)
                    .map(user -> {
                        user.setEducation(newData);
                        return repository.save(user);
                    })
                    .orElseGet(() -> {
                        User newuser = new User();
                        newuser.setUsername("empty");
                        return newuser;
                    });
        } else {
            return repository.findById(id)
                    .map(user -> {
                        user.setSkills(newData);
                        return repository.save(user);
                    })
                    .orElseGet(() -> {
                        User newuser = new User();
                        newuser.setUsername("empty");
                        return newuser;
                    });
        }

    }

//        else if (splitted[0].equals("workexp")){
//            return repository.findById(id)
//                    .map(user -> {
//                        InfoText info = new InfoText(splitted[1]);
//                        user.setWork_experience(info);
//                        return repository.save(user);
//                    })
//                    .orElseGet(() -> {
//                        User newuser = new User();
//                        newuser.setUsername("empty");
//                        return newuser;
//                    });
//        }
//        else if (splitted[0].equals("email")){
//            return repository.findById(id)
//                    .map(user -> {
//                        user.setFirstName(splitted[1]);
//                        return repository.save(user);
//                    })
//                    .orElseGet(() -> {
//                        User newuser = new User();
//                        newuser.setUsername("empty");
//                        return newuser;
//                    });
//        }else if (splitted[0].equals("email")){
//            return repository.findById(id)
//                    .map(user -> {
//                        user.setFirstName(splitted[1]);
//                        return repository.save(user);
//                    })
//                    .orElseGet(() -> {
//                        User newuser = new User();
//                        newuser.setUsername("empty");
//                        return newuser;
//                    });
//        }
//

//    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/user/{id}")
    void deleteUser(@PathVariable String id) {
        repository.deleteById(id);
    }

}
