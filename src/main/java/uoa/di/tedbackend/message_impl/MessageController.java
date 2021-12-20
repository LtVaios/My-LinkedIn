package uoa.di.tedbackend.message_impl;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
class MessageController {

    private final MessageRepository repository;

    MessageController(MessageRepository repository) {
        this.repository = repository;
    }

    // Aggregate root

    @CrossOrigin(origins = "*")
    @GetMapping("/messages")
    List<Message> all() {
        return repository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/messages/{userId}")
    List<Message> all(@PathVariable (value = "userId") int userid) {
        return repository.findMessagesByUser(userid);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/messages")
    Message newMessage(@RequestBody Message newMessage) {
        return repository.save(newMessage);
    }


    @CrossOrigin(origins = "*")
    @DeleteMapping("/messages/{userId}")
    void deleteMessages(@PathVariable (value = "userId") int userid) {
        List<Message> messages=repository.findMessagesByUser(userid);
        for(Message m:messages){
            repository.deleteById(m.getId());
        }
    }

}
