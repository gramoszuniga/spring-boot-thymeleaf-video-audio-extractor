package com.einfari.springbootthymeleafvideoaudioextractor.resource;

import com.einfari.springbootthymeleafvideoaudioextractor.common.MediaException;
import com.einfari.springbootthymeleafvideoaudioextractor.common.StorageException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-25
 **/
@SuppressWarnings("unused")
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoHandlerFound(Model model) {
        model.addAttribute("message", "Page nor found.");
        return "error";
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleHttpRequestMethodNotSupported(Model model) {
        model.addAttribute("message", "Page nor found.");
        return "error";
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public String handleSizeLimitExceeded(Model model, IOException e) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler(StorageException.class)
    public String handleStorage(Model model, RuntimeException e) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler(MediaException.class)
    public String handleMedia(Model model, RuntimeException e) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

}