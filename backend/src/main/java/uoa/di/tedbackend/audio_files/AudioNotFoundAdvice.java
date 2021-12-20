package uoa.di.tedbackend.audio_files;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class AudioNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(AudioNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String AudioNotFoundHandler(AudioNotFoundException ex) {
        return ex.getMessage();
    }
}
