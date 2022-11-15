package com.einfari.springbootthymeleafvideoaudioextractor.resource;

import com.einfari.springbootthymeleafvideoaudioextractor.application.FFmpegComponent;
import com.einfari.springbootthymeleafvideoaudioextractor.application.StorageComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;

import java.net.MalformedURLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-11-14
 **/
@ExtendWith(MockitoExtension.class)
class FileControllerTest {

    @Mock
    private Model model;
    @Mock
    private FFmpegComponent FFmpegComponent;
    @Mock
    private StorageComponent storageComponent;
    private FileController fileController;

    @BeforeEach
    void setUp() {
        fileController = new FileController(FFmpegComponent, storageComponent);
    }

    @Test
    void canUpload() {
        String expected = "download";
        List<String> filenameList = List.of("filename1", "filename2");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("name", "content".getBytes());
        when(FFmpegComponent.extractAudio(mockMultipartFile)).thenReturn(filenameList);
        String actual = fileController.upload(mockMultipartFile, model);
        verify(FFmpegComponent, times(1)).extractAudio(eq(mockMultipartFile));
        verify(model, times(1)).addAttribute("filenameList", filenameList);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canDownload() throws MalformedURLException {
        String filename = "filename";
        Resource resource = new UrlResource("file://" + filename);
        ResponseEntity<Resource> expected = ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
        when(storageComponent.read(filename)).thenReturn(resource);
        ResponseEntity<Resource> actual = fileController.download(filename);
        verify(storageComponent, times(1)).read(eq(filename));
        assertThat(actual).isEqualTo(expected);
    }

}