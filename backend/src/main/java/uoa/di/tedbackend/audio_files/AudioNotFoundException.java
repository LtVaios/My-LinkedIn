package uoa.di.tedbackend.audio_files;

class AudioNotFoundException extends RuntimeException {

    AudioNotFoundException(Long id) {
        super("Could not find Audio " + id);
    }
}
