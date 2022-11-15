package com.einfari.springbootthymeleafvideoaudioextractor.resource;

import com.einfari.springbootthymeleafvideoaudioextractor.common.MediaException;
import com.einfari.springbootthymeleafvideoaudioextractor.common.StorageException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-11-14
 **/
@ExtendWith(MockitoExtension.class)
class ExceptionControllerTest {

    public static final String MESSAGE = "message";
    public static final String PAGE_NOT_FOUND = "Page not found.";
    @Mock
    private Model model;
    private ExceptionController exceptionController;

    @BeforeEach
    void setUp() {
        exceptionController = new ExceptionController();
    }

    @Test
    void canHandleNoHandlerFound() {
        String expected = "error";
        String actual = exceptionController.handleNoHandlerFound(model);
        verify(model, times(1)).addAttribute(eq(MESSAGE), eq(PAGE_NOT_FOUND));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canHandleHttpRequestMethodNotSupported() {
        String expected = "error";
        String actual = exceptionController.handleHttpRequestMethodNotSupported(model);
        verify(model, times(1)).addAttribute(eq(MESSAGE), eq(PAGE_NOT_FOUND));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canHandleSizeLimitExceeded() {
        String expected = "error";
        long actualSize = 131756432L;
        long permittedSize = 104857600L;
        String message = "the request was rejected because its size (" + actualSize + ") exceeds the configured " +
                "maximum (" + permittedSize + ")";
        SizeLimitExceededException sizeLimitExceededException = new SizeLimitExceededException(message, actualSize,
                permittedSize);
        String actual = exceptionController.handleSizeLimitExceeded(model, sizeLimitExceededException);
        verify(model, times(1)).addAttribute(eq(MESSAGE), eq(message));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canHandleStorage() {
        String expected = "error";
        String message = "File not found.";
        StorageException storageException = new StorageException(message);
        String actual = exceptionController.handleStorage(model, storageException);
        verify(model, times(1)).addAttribute(eq(MESSAGE), eq(message));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canHandleMedia() {
        String expected = "error";
        String message = "Audio format could not be identified.";
        MediaException mediaException = new MediaException(message);
        String actual = exceptionController.handleMedia(model, mediaException);
        verify(model, times(1)).addAttribute(eq(MESSAGE), eq(message));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void canHandleIOException() {
        String expected = "error";
        String message = "Oops, something went wrong.";
        IOException ioException = new IOException(message);
        String actual = exceptionController.handleIOException(model, ioException);
        verify(model, times(1)).addAttribute(eq(MESSAGE), eq(message));
        assertThat(actual).isEqualTo(expected);
    }

}