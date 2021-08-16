package uoa.di.tedbackend.image;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.Deflater;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.post_impl.PostRepository;
import uoa.di.tedbackend.user_impl.User;
import uoa.di.tedbackend.user_impl.UserRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "images")
class ImageController {

    private final ImageRepository repository;
    private final UserRepository urepository;
    private final PostRepository prepository;

    ImageController(ImageRepository repository, UserRepository urepository, PostRepository prepository) {
        this.repository = repository;
        this.urepository = urepository;
        this.prepository = prepository;
    }


    @PostMapping("/upload/user/{id}")
    @CrossOrigin(origins = "*")
    public Image uploadUserImage(@RequestParam("imageFile") MultipartFile file, @PathVariable("id") int userid) throws IOException {
        //upload one picture for user profil
        System.out.println("in upload");
        System.out.println("Original Image Byte Size - " + file.getBytes().length);

        User user = urepository.findById(userid).get();
        Image img = null;
        try {
            img = new Image(file.getOriginalFilename(), file.getContentType(),
                    file.getBytes());
            user.setImg(img);
            urepository.save(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return repository.save(img);

    }

//    @PostMapping("/upload/post/{id}")
//    @CrossOrigin(origins = "*")
//    public String uploadPostImages(@RequestParam("imageFile") List<MultipartFile> files, @PathVariable("id") int postid) throws IOException {
//        //upload multiple pictures for one post
//        System.out.println("in upload");
//        Post post = prepository.findById(postid).get();
//
//        for (MultipartFile file: files) {
//            System.out.println("Original Image Byte Size - " + file.getBytes().length);
//            Image img = null;
//            try {
//                img = new Image(file.getOriginalFilename(), file.getContentType(),
//                        file.getBytes());
//                post.getImg().add(img);
//                prepository.save(post);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            repository.save(img);
//        }
//        return "success";
//    }

    @PostMapping("/upload/post/{id}")
    @CrossOrigin(origins = "*")
    public Image uploadPostImage(@RequestParam("imageFile") MultipartFile file, @PathVariable("id") int postid) throws IOException {
        //upload multiple pictures for one post
        System.out.println("in upload");
        Post post = prepository.findById(postid).get();

        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        Image img = null;
        Image newimg = null;
        try {
            img = new Image(file.getOriginalFilename(), file.getContentType(),
                    file.getBytes());
            newimg = repository.save(img);
            Set<Image> imageslist = post.getImages();
            imageslist.add(newimg);
            post.setImages(imageslist);
            prepository.save(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newimg;
    }

    @GetMapping("/post/{id}")
    @CrossOrigin(origins = "*")
    public List<Image> getPostImage(@PathVariable("id") int post_id) {
        //return pictures for one post
        return repository.findPostImages(post_id);
    }

    @PutMapping("/upload/user/{id}")
    @CrossOrigin(origins = "*")
    public Image replaceUserImage(@RequestParam("imageFile") MultipartFile file, @PathVariable("id") int userid) throws IOException {
        System.out.println("in upload");
        System.out.println("Original Image Byte Size - " + file.getBytes().length);

        User user = urepository.findById(userid).get();
        if (user.getImg() == null) {
            Image img = null;
            try {
                img = new Image(file.getOriginalFilename(), file.getContentType(),
                        file.getBytes());
                user.setImg(img);
                urepository.save(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return repository.save(img);
        }
        else {
            Image img = user.getImg();
            img.setName(file.getOriginalFilename());
            img.setType(file.getContentType());
            img.setPicByte(file.getBytes());
            urepository.save(user);
            return repository.save(img);
        }
    }


    @GetMapping(path = { "/get/{id}" })
    public Image getImage(@PathVariable("id") long imageId){
        return repository.findById(imageId)
                .orElseThrow(()->new ImageNotFoundException(imageId));
    }


    @DeleteMapping("/images/{id}")
    void deleteImage(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
