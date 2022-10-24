package com.einfari.springbootthymeleafvideoaudioextractor.resource;

import com.einfari.springbootthymeleafvideoaudioextractor.application.MediaComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-21
 **/
@SuppressWarnings("unused")
@Slf4j
@RequiredArgsConstructor
@Controller
public class FileController {

    private final MediaComponent mediaComponent;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String filename = mediaComponent.extractAudio(file);
            model.addAttribute("filename", filename);
            return "download";
        } catch (IOException e) {
            log.error(e.getMessage());
            return "/";
        }
    }

}