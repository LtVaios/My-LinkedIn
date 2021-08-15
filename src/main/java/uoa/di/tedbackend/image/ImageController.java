package uoa.di.tedbackend.image;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Random;
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
import uoa.di.tedbackend.user_impl.User;
import uoa.di.tedbackend.user_impl.UserRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "images")
class ImageController {

    private final ImageRepository repository;
    private final UserRepository urepository;

    ImageController(ImageRepository repository, UserRepository urepository) {
        this.repository = repository;
        this.urepository = urepository;
    }

    @PostMapping("/upload/user/{id}")
    @CrossOrigin(origins = "*")
    public Image uploadOneImage(@RequestParam("imageFile") MultipartFile file, @PathVariable("id") int userid) throws IOException {
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
