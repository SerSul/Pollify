package ru.coursework.pollify.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/questionnaire/create").setViewName("createQuestionnaire");
        registry.addViewController("/questionnaire/question/add").setViewName("addQuestionModal");
        registry.addRedirectViewController("/", "/home");
    }
}
