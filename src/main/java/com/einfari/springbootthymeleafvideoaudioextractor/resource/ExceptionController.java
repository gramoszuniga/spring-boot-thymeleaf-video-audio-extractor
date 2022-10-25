package com.einfari.springbootthymeleafvideoaudioextractor.resource;

import com.einfari.springbootthymeleafvideoaudioextractor.common.MediaException;
import com.einfari.springbootthymeleafvideoaudioextractor.common.StorageException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-25
 **/
@SuppressWarnings("unused")
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(StorageException.class)
    public String handleFileNotFound(Model model, RuntimeException e) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler(MediaException.class)
    public String handleMedia(Model model, RuntimeException e) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }

}