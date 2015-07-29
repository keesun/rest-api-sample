package me.whiteship.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Baik KeeSun
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello Spring Boot";
    }

}
