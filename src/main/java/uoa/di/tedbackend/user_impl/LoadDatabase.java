package uoa.di.tedbackend.user_impl;

import lombok.extern.slf4j.Slf4j;

import java.lang.Math;
import org.ejml.simple.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        //repository.deleteAll();
        this.MF();
        return args -> {
            BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
            log.info("Preloading " + repository.save(new User(1,"bilbo@gmail.com",bCryptPasswordEncoder.encode("1234"),"Bilbo", "Baggins", "1234",false)));
            log.info("Preloading " + repository.save(new User(4,"bob@gmail.com",bCryptPasswordEncoder.encode("1234"),"Bob", "Baggins", "1234",false)));
            log.info("Preloading " + repository.save(new User(2,"sam@gmail.com",bCryptPasswordEncoder.encode("4321"),"Sam", "Baggins", "4321",false)));
            log.info("Preloading " + repository.save(new User(3,"frodo@gmail.com",bCryptPasswordEncoder.encode("5678"),"Frodo", "Baggins", "5678",true)));
        };
    }

    void MF(){
        SimpleMatrix userMatrix = new SimpleMatrix(
                new double[][] {
                        new double[] {1},
                        new double[] {2},
                        new double[] {3},
                        new double[] {4},
                        new double[] {5},
                        new double[] {6}

                }
        );

        SimpleMatrix postMatrix = new SimpleMatrix(
                new double[][] {
                        new double[] {1, 2, 3, 4}
                }
        );

        SimpleMatrix dataMatrix = new SimpleMatrix(
                new double[][] {
                        new double[] {5, 3, 0, 1},
                        new double[] {4, 0, 0, 1},
                        new double[] {1, 1, 0, 5},
                        new double[] {1, 0, 0, 4},
                        new double[] {0, 1, 5, 4},
                        new double[] {2, 1, 3, 0}
                }
        );

        int k=3;
        double err=999999,e,prev_err;
        double h=0.00001;
        double x_;
        Random rand = new Random();
        SimpleMatrix V = SimpleMatrix.random_DDRM(userMatrix.numRows(),k,1,50,rand);
        SimpleMatrix F = SimpleMatrix.random_DDRM(k,postMatrix.numCols(),1,50,rand);
        for(int iter=0; iter<=10000; iter++){
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
        SimpleMatrix res = V.mult(F);
        System.out.println("Result: ");
        res.print();
    }
}
