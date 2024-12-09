package ru.coursework.pollify.mappings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.coursework.pollify.entity.Questionnaire;
import ru.coursework.pollify.service.QuestionnaireService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @GetMapping("/load/{uri}")
    public ModelAndView getQuestionnaire(@PathVariable String uri, RedirectAttributes redirectAttributes) {
        try {
            var questionnaire = questionnaireService.findQuestionnaireByUri(UUID.fromString(uri));
            return new ModelAndView("questionnaire")
                    .addObject("questionnaire", questionnaire)
                    .addObject("uri", uri);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", true);
            return new ModelAndView("redirect:/home");
        }
    }

    @GetMapping("/create")
    public ModelAndView createQuestionnaire() {
        return new ModelAndView("questionnaireModal");
    }


}
