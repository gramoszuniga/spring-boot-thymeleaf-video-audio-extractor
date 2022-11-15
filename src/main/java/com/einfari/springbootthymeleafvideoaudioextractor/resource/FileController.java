package com.einfari.springbootthymeleafvideoaudioextractor.resource;

import com.einfari.springbootthymeleafvideoaudioextractor.application.FFmpegComponent;
import com.einfari.springbootthymeleafvideoaudioextractor.application.StorageComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-21
 **/
@SuppressWarnings("unused")
@RequiredArgsConstructor
@Controller
public class FileController {

    private final FFmpegComponent FFmpegComponent;
    private final StorageComponent storageComponent;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        List<String> filenameList = FFmpegComponent.extractAudio(file);
        model.addAttribute("filenameList", filenameList);
        return "download";
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws MalformedURLException {
        Resource resource = storageComponent.read(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }

}