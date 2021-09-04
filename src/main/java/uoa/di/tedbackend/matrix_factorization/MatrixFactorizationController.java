package uoa.di.tedbackend.matrix_factorization;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.post_impl.PostRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MatrixFactorizationController {
//    @Autowired
    private final matrix_factorization mf;

    private final PostRepository prepository;

    MatrixFactorizationController(matrix_factorization mf, PostRepository prepository) {
        this.mf = mf;
        this.prepository = prepository;
    }

    @GetMapping("/recommend/posts/{user_id}")
    List<Post> getPostsOrdered(@PathVariable int user_id){
        if (mf.post_ids==null) return new ArrayList<>();
        List<Post> posts = new ArrayList<>();
        List<Integer> post_ids = mf.post_recommendations(user_id);
        System.out.println("printing post ids"+post_ids);
        for (int id:post_ids){
            System.out.println("id:"+id);
            posts.add(prepository.findById(id).get());
        }
        return posts;
    }
}
