package com.einfari.springbootthymeleafvideoaudioextractor.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-10-21
 **/
@SuppressWarnings("unused")
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

}