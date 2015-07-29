package me.whiteship.web;

import me.whiteship.exception.UsernameDuplicatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Baik KeeSun
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UsernameDuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String usernameDuplicatedException(UsernameDuplicatedException e) {
        return "{\"code\":\"error\"}";
    }

}
