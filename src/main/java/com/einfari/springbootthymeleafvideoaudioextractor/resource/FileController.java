package com.einfari.springbootthymeleafvideoaudioextractor.resource;

import com.einfari.springbootthymeleafvideoaudioextractor.application.MediaComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-21
 **/
@SuppressWarnings("unused")
@RequiredArgsConstructor
@Controller
public class FileController {

    private final MediaComponent mediaComponent;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        model.addAttribute("codecName", mediaComponent.getAudioCodecName(file));
        return "download";
    }

}