package com.einfari.springbootthymeleafvideoaudioextractor.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-11-14
 **/
class IndexControllerTest {

    private IndexController indexController;

    @BeforeEach
    void setUp() {
        indexController = new IndexController();
    }

    @Test
    void canShowIndex() {
        String expected = "index";
        String actual = indexController.index();
        assertThat(actual).isEqualTo(expected);
    }

}