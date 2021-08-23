package uoa.di.tedbackend.video_impl;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uoa.di.tedbackend.post_impl.Post;
import uoa.di.tedbackend.post_impl.PostRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "videos")
class VideoController {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "C://temp//";

    private final VideoRepository repository;
    private final PostRepository prepository;

    VideoController(VideoRepository repository, PostRepository prepository) {
        this.repository = repository;
        this.prepository = prepository;
    }

    // Aggregate root

    @PostMapping("/upload/{post_id}")
    public String singleFileUpload(@RequestParam("videoFile") MultipartFile file, @PathVariable int post_id,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/videos/uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER
                    + file.getOriginalFilename()
//                    .substring(file.getOriginalFilename().lastIndexOf('/'), file.getOriginalFilename().lastIndexOf('.'))
//                    + new Random().nextInt(1 << 20)
//                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))
            );
            Files.write(path, bytes);

            Video img = new Video(path.toString());
            repository.save(img);

            /* Add video to post */
            Post post = prepository.findById(post_id).get();
            Set<Video> videoslist = post.getVideos();
            videoslist.add(img);
            post.setVideos(videoslist);
            prepository.save(post);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/videos/uploadStatus";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> singleFileDownload(@PathVariable long id) throws IOException {

        Video vid = repository.getById(id);

        Path path = Paths.get(vid.getPath());
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String contentType = "video/mp4";
        System.out.println(resource);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

    @GetMapping("")
    List<Video> all() {
        return repository.findAll();
    }

    @PostMapping("")
    Video newVideo(@RequestBody Video newVideo) {
        return repository.save(newVideo);
    }

    // Single item

    @GetMapping("/{id}")
    Video one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException(id));
    }


    @PutMapping("/{id}")
    Video replaceVideo(@RequestBody Video newVideo, @PathVariable Long id) {

        return repository.findById(id)
                .map(Video -> {
                    Video.setPath(newVideo.getPath());
                    return repository.save(Video);
                })
                .orElseGet(() -> {
                    newVideo.setId(id);
                    return repository.save(newVideo);
                });
    }

    @DeleteMapping("/{id}")
    void deleteVideo(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
