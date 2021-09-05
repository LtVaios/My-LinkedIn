package uoa.di.tedbackend.matrix_factorization;

import org.ejml.data.Matrix;
import uoa.di.tedbackend.joblike_impl.JobLikeRepository;
import uoa.di.tedbackend.likes_impl.Likes;
import uoa.di.tedbackend.likes_impl.LikesRepository;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.post_impl.PostRepository;
import uoa.di.tedbackend.user_impl.User;
import uoa.di.tedbackend.user_impl.UserRepository;

import org.ejml.simple.SimpleMatrix;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class matrix_factorization {
//    private SimpleMatrix userMatrix;
//    private SimpleMatrix postMatrix;
//    private SimpleMatrix jobMatrix;

    private SimpleMatrix dataMatrix;
    private SimpleMatrix recommendationsMatrix;

    List<Integer> user_ids;
    List<Integer> post_ids;

    private final PostRepository post_repository;
    private final UserRepository user_repository;
    private final LikesRepository likes_repository;
    private final JobLikeRepository joblike_repository;

    matrix_factorization(PostRepository repository, UserRepository urepository, LikesRepository likes_repository, JobLikeRepository joblike_repository) {
        this.post_repository = repository;
        this.user_repository=urepository;
        this.likes_repository = likes_repository;
        this.joblike_repository = joblike_repository;
    }

    List<Integer> post_recommendations(int user_id){
        int user_index = user_ids.indexOf(user_id);
        Map<Integer, Double> ratings = null;
        for (int i=0; i<recommendationsMatrix.numCols(); i++){
            if (dataMatrix.get(user_index, i) > 0){
                ratings.put(i, recommendationsMatrix.get(user_index, i));
            }
        }
//        SimpleMatrix ratings = recommendationsMatrix.rows(user_index, user_index);
        List<Map.Entry<Integer, Double>> list = new ArrayList<>(ratings.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<Integer, Double> result = new LinkedHashMap<>();
        for (Map.Entry<Integer, Double> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return null;
    }

    public void mf_posts(){
        List<Integer> user_ids = user_repository.findAll().stream().map(User::getId).collect(Collectors.toList());
        List<Integer> post_ids = post_repository.findAll().stream().map(Post::getId).collect(Collectors.toList());
//        userMatrix = new SimpleMatrix((Matrix) user_ids);
//        postMatrix = new SimpleMatrix((Matrix) post_ids);

        List<Likes> likes = likes_repository.findAll();
        dataMatrix = new SimpleMatrix(user_ids.size(),post_ids.size());

        for (Likes like: likes){
            int user_id = like.getUser().getId();
            int user_index = user_ids.indexOf(user_id);

            int post_id = like.getPost().getId();
            int post_index = user_ids.indexOf(post_id);

            dataMatrix.set(user_index, post_index, dataMatrix.get(user_index, post_index) + 1);
        }

        int k=3;
        double h=0.00001;
        Random rand = new Random();
        SimpleMatrix V = SimpleMatrix.random_DDRM(dataMatrix.numRows(),k,1,5,rand);
        SimpleMatrix F = SimpleMatrix.random_DDRM(k,dataMatrix.numCols(),1,5,rand);

        algorithm(dataMatrix, V, F, k, h);
    }


    private void algorithm(SimpleMatrix dataMatrix, SimpleMatrix V, SimpleMatrix F, int k, double h){
        algorithm(dataMatrix, V, F, k, h, 10000);
    }

    private void algorithm(SimpleMatrix dataMatrix, SimpleMatrix V, SimpleMatrix F, int k, double h, int max_iters){

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
        recommendationsMatrix = V.mult(F);
        System.out.println("Result: ");
        recommendationsMatrix.print();
    }
}
