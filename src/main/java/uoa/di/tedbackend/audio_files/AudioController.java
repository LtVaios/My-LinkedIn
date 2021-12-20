package uoa.di.tedbackend.audio_files;

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
@RequestMapping(path = "audio")
class AudioController {

    private final AudioRepository repository;
    private final PostRepository prepository;

    AudioController(AudioRepository repository, PostRepository prepository) {
        this.repository = repository;
        this.prepository = prepository;
    }



    @PostMapping("/upload/post/{id}")
    @CrossOrigin(origins = "*")
    public Audio uploadPostAudio(@RequestParam("audioFile") MultipartFile file, @PathVariable("id") int postid) throws IOException {
        //upload multiple pictures for one post
        System.out.println("in upload");
        Post post = prepository.findById(postid).get();

        System.out.println("Original Audio Byte Size - " + file.getBytes().length);
        Audio aud = null;
        Audio newaud = null;
        try {
            aud = new Audio(file.getOriginalFilename(), file.getContentType(),
                    file.getBytes());
            newaud = repository.save(aud);
            Set<Audio> audioslist = post.getAudios();
            audioslist.add(newaud);
            post.setAudios(audioslist);
            prepository.save(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newaud;
    }

    @GetMapping(path = { "/get/{id}" })
    public Audio getImage(@PathVariable("id") long AudioId){
        return repository.findById(AudioId)
                .orElseThrow(()->new AudioNotFoundException(AudioId));
    }


    @DeleteMapping("/audio/{id}")
    void deleteAudio(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
