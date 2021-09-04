package uoa.di.tedbackend.post_view;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import uoa.di.tedbackend.matrix_factorization.matrix_factorization;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "postviews")
class PostViewController {

    private final PostViewRepository repository;
    private final matrix_factorization mf;

    PostViewController(PostViewRepository repository, matrix_factorization mf) {
        this.repository = repository;
        this.mf = mf;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/")
    List<PostView> allViews() {
        return repository.findAll();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/ofuser/{userId}")
    List<PostView> PostViewsbyuser(@PathVariable (value = "userId") int userId) {
        try {
            List<PostView> views;
            views = repository.findPostViewsByUser(userId);
            return views;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting likes of user");
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/ofpost/{postId}")
    List<PostView> viewsbypost(@PathVariable (value = "postId") int postId) {
        try {
            List<PostView> views;
            views=repository.findPostViewsByPost(postId);
            return views;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting likes of post");
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/touser")
    List<PostView> viewstouserspost(@RequestParam int userid){
        try {
            List<PostView> views;
            views=repository.findPostViewsToUsersPosts(userid);
            return views;
        }
        catch(Exception e){
            throw new RuntimeException("Error getting likes of post");
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/addview")
    PostView newLike(@RequestBody PostView view) {
        PostView temp = repository.save(view);
        System.out.println("in post view");
        mf.mf_posts();
        return temp;
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }

}
