package ru.coursework.pollify.configurations;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(HttpSession session) {
        session.invalidate();
        return "home";
    }



    @GetMapping("favicon.ico")
    @ResponseBody
    public void dummyFavicon() {
        //nop
    }

}
