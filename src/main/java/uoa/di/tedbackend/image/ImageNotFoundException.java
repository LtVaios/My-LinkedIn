package uoa.di.tedbackend.image;

class ImageNotFoundException extends RuntimeException {

    ImageNotFoundException(Long id) {
        super("Could not find image " + id);
    }
}
