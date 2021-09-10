package uoa.di.tedbackend.matrix_factorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uoa.di.tedbackend.comment_impl.Comment;
import uoa.di.tedbackend.comment_impl.CommentRepository;
import uoa.di.tedbackend.job_impl.Job;
import uoa.di.tedbackend.job_impl.JobRepository;
import uoa.di.tedbackend.job_view.JobView;
import uoa.di.tedbackend.job_view.JobViewRepository;
import uoa.di.tedbackend.application_impl.Application;
import uoa.di.tedbackend.application_impl.ApplicationRepository;
import uoa.di.tedbackend.likes_impl.Likes;
import uoa.di.tedbackend.likes_impl.LikesRepository;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.post_impl.PostRepository;
import uoa.di.tedbackend.post_view.PostView;
import uoa.di.tedbackend.post_view.PostViewRepository;
import uoa.di.tedbackend.user_impl.User;
import uoa.di.tedbackend.user_impl.UserRepository;

import org.ejml.simple.SimpleMatrix;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.min;

@Service("ms")
public class matrix_factorization {
//    private SimpleMatrix userMatrix;
//    private SimpleMatrix postMatrix;
//    private SimpleMatrix jobMatrix;

    private SimpleMatrix post_dataMatrix;
    private SimpleMatrix post_recommendationsMatrix;

    private SimpleMatrix job_dataMatrix;
    private SimpleMatrix job_recommendationsMatrix;

    /* 2 user ids lists in case the lists are different at calling time */
    List<Integer> post_user_ids;
    List<Integer> post_ids;
    List<Integer> job_ids;
    List<Integer> job_user_ids;

    private final PostRepository post_repository;
    private final UserRepository user_repository;
    private final LikesRepository likes_repository;
    private final ApplicationRepository application_repository;
    private final JobRepository job_repository;
    private final CommentRepository comment_repository;
    private final PostViewRepository postview_repository;
    private final JobViewRepository jobview_repository;

    @Autowired
    public matrix_factorization(PostRepository repository, UserRepository urepository, LikesRepository likes_repository, ApplicationRepository application_repository, JobRepository job_repository, CommentRepository comment_repository, PostViewRepository postview_repository, JobViewRepository jobview_repository) {
        this.post_repository = repository;
        this.user_repository=urepository;
        this.likes_repository = likes_repository;
        this.application_repository = application_repository;
        this.job_repository = job_repository;
        this.comment_repository = comment_repository;
        this.postview_repository = postview_repository;
        this.jobview_repository = jobview_repository;
        post_user_ids = null;
        post_ids = null;
        job_ids = null;
        job_user_ids = null;
    }

    /* posts */

    public List<Integer> post_recommendations(int user_id, int size){
        int user_index = post_user_ids.indexOf(user_id); /* get index of user */

        /* put all ratings in a map{post_index->rating}*/
        Map<Integer, Double> ratings = new HashMap<>();
        for (int i = 0; i< post_recommendationsMatrix.numCols(); i++){
            if (post_dataMatrix.get(user_index, i) == 0){
                ratings.put(i, post_recommendationsMatrix.get(user_index, i));
            }
        }

        /* sort by rating */
        List<Map.Entry<Integer, Double>> list = new ArrayList<>(ratings.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<Integer, Double> result = new LinkedHashMap<>();
        for (Map.Entry<Integer, Double> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        System.out.println(result);

        List<Integer> ids_ordered = new ArrayList<>();
        int index;
        for (Map.Entry<Integer, Double> entry : list) {
            index = entry.getKey();
            ids_ordered.add(post_ids.get(index));
        }
        System.out.println(ids_ordered);

        return ids_ordered; //.subList(0, min(ids_ordered.size(), size))
    }

//    public List<Integer> findTopK(List<Map.Entry<Integer, Double>> input, int k) {
//        PriorityQueue<Map.Entry<Integer, Double>> maxHeap = new PriorityQueue<>();
//
//        input.forEach(number -> {
//            maxHeap.add(number);
//
//            if (maxHeap.size() > k) {
//                maxHeap.poll();
//            }
//        });
//
//        List<Integer> topKList = new ArrayList<>(maxHeap);
//        Collections.reverse(topKList);
//
//        return topKList;
//    }

    public void mf_posts(){
        /* Applies matrix factorization for posts.
        * It fills a users*posts matrix with ratings based on likes and comments.
        * If a user has neither likes nor comments then we use views as ratings instead. */

        post_user_ids = user_repository.findAll().stream().map(User::getId).collect(Collectors.toList()); /* get all user ids in a list */
        post_ids = post_repository.findAll().stream().map(Post::getId).collect(Collectors.toList()); /* get all post ids in a list */

        post_dataMatrix = new SimpleMatrix(post_user_ids.size(),post_ids.size()); /* dataMatrix' size will be number_of_users*number_of_posts */

        for (int userid: post_user_ids){
            int user_index = post_user_ids.indexOf(userid);

            List<Likes> user_likes = likes_repository.findLikesByUser(userid);
            List<Comment> user_comments = comment_repository.findCommentsByUser(userid);

            if (user_likes.size()+user_comments.size() == 0){ /* if user hasn't made any comments or likes then use views */
                List<PostView> views = postview_repository.findPostViewsByUser(userid);
                for (PostView view: views){
                    int post_id = view.getPost().getId();
                    int post_index = post_ids.indexOf(post_id);

                    post_dataMatrix.set(user_index, post_index, post_dataMatrix.get(user_index, post_index) + 1);
                }
            } else {
                for (Likes like: user_likes){
                    int post_id = like.getPost().getId();
//                    System.out.println("post id:"+ post_id);
//                    System.out.println("list_ids:"+post_user_ids);
                    int post_index = post_ids.indexOf(post_id);
//                    System.out.println("post index:"+post_index);

                    post_dataMatrix.set(user_index, post_index, post_dataMatrix.get(user_index, post_index) + 1);
                }
                for (Comment comment: user_comments){

                    int post_id = comment.getPost().getId();
                    int post_index = post_ids.indexOf(post_id);

                    post_dataMatrix.set(user_index, post_index, post_dataMatrix.get(user_index, post_index) + 1);
                }
            }
        }

        int k=3;
        double h=0.00001;
        Random rand = new Random();
        SimpleMatrix V = SimpleMatrix.random_DDRM(post_dataMatrix.numRows(),k,1,5,rand);
        SimpleMatrix F = SimpleMatrix.random_DDRM(k, post_dataMatrix.numCols(),1,5,rand);

        post_recommendationsMatrix = algorithm(post_dataMatrix, V, F, k, h);
    }

    /* jobs */
    public List<Integer> job_recommendations(int user_id){
        int user_index = job_user_ids.indexOf(user_id); /* get index of user */

        /* put all ratings in a map{post_index->rating}*/
        Map<Integer, Double> ratings =  new HashMap<>();
        for (int i = 0; i< job_recommendationsMatrix.numCols(); i++){
            if (job_dataMatrix.get(user_index, i) == 0){
                ratings.put(i, job_recommendationsMatrix.get(user_index, i));
            }
        }

        /* sort by rating */
        List<Map.Entry<Integer, Double>> list = new ArrayList<>(ratings.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<Integer, Double> result = new LinkedHashMap<>();
        for (Map.Entry<Integer, Double> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        System.out.println(result);

        List<Integer> ids_ordered = new ArrayList<>();
        int index;
        for (Map.Entry<Integer, Double> entry : list) {
            index = entry.getKey();
            ids_ordered.add(job_ids.get(index));
        }
        System.out.println(ids_ordered);

        return ids_ordered;
    }

    public void mf_jobs(){
        /* Applies matrix factorization for jobs.
         * It fills a users*posts matrix with ratings based on likes.
         * If a user has not likes then we use views as ratings instead. */

        job_user_ids = user_repository.findAll().stream().map(User::getId).collect(Collectors.toList()); /* get all user ids in a list */
        job_ids = job_repository.findAll().stream().map(Job::getId).collect(Collectors.toList()); /* get all job ids in a list */

        job_dataMatrix = new SimpleMatrix(job_user_ids.size(),job_ids.size()); /* dataMatrix' size will be number_of_users*number_of_jobs */

        for (int userid: job_user_ids){
            int user_index = job_user_ids.indexOf(userid);

            List<Application> user_likes = application_repository.findApplicationsByUser(userid);

            if (user_likes.size() == 0){ /* if user hasn't made any comments or likes then use views */
                List<JobView> views = jobview_repository.findJobViewsByUser(userid);
                for (JobView view: views){
                    int job_id = view.getJob().getId();
                    int job_index = job_ids.indexOf(job_id);

                    job_dataMatrix.set(user_index, job_index, job_dataMatrix.get(user_index, job_index) + 1);
                }
            } else {
                for (Application like: user_likes){
                    int job_id = like.getJob().getId();
//                    System.out.println("post id:"+ post_id);
//                    System.out.println("list_ids:"+post_user_ids);
                    int job_index = job_ids.indexOf(job_id);
//                    System.out.println("post index:"+post_index);

                    job_dataMatrix.set(user_index, job_index, job_dataMatrix.get(user_index, job_index) + 1);
                }
            }
        }

        int k=3;
        double h=0.00001;
        Random rand = new Random();
        SimpleMatrix V = SimpleMatrix.random_DDRM(job_dataMatrix.numRows(),k,1,5,rand);
        SimpleMatrix F = SimpleMatrix.random_DDRM(k, job_dataMatrix.numCols(),1,5,rand);

        job_recommendationsMatrix = algorithm(job_dataMatrix, V, F, k, h);
    }

    private SimpleMatrix algorithm(SimpleMatrix dataMatrix, SimpleMatrix V, SimpleMatrix F, int k, double h){
        return algorithm(dataMatrix, V, F, k, h, 10000);
    }

    private SimpleMatrix algorithm(SimpleMatrix dataMatrix, SimpleMatrix V, SimpleMatrix F, int k, double h, int max_iters){

        double err=999999,e,prev_err;
        double x_;
        for(int iter=0; iter<=max_iters; iter++){
            for(int i=0; i<dataMatrix.numRows(); i++){
                for(int j=0; j<dataMatrix.numCols(); j++) {
                    if (dataMatrix.get(i, j) > 0){
                        x_=0;
                        for(int n=0; n<k; n++)
                            x_ += V.get(i,n)*F.get(n,j);
                        e = dataMatrix.get(i, j) - x_;
                        for (int n = 0; n < k; n++) {
                            V.set(i, n, V.get(i, n) + h * 2 * e * F.get(n, j));
                            F.set(n, j, F.get(n, j) + h * 2 * e * V.get(i, n));
                        }
                    }
                }
            }
            prev_err=err;
            err=0;
            for(int i=0; i<dataMatrix.numRows(); i++){
                for(int j=0; j<dataMatrix.numCols(); j++) {
                    if (dataMatrix.get(i, j) > 0) {
                        x_=0;
                        for(int n=0; n<k; n++)
                            x_ += V.get(i,n)*F.get(n,j);
                        err += Math.pow(dataMatrix.get(i, j) - x_, 2);
                    }
                }
            }
            if(prev_err <= err ){
                System.out.println("Iter: "+iter);
                break;
            }
        }
        System.out.println("err: "+err);
        System.out.println("Initial: ");
        dataMatrix.print();
        SimpleMatrix recommendationsMatrix = V.mult(F);
        System.out.println("Result: ");
        recommendationsMatrix.print();
        return recommendationsMatrix;
    }
}
