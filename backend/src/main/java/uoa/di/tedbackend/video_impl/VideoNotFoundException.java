package uoa.di.tedbackend.video_impl;

class VideoNotFoundException extends RuntimeException {

    VideoNotFoundException(Long id) {
        super("Could not find image " + id);
    }
}
