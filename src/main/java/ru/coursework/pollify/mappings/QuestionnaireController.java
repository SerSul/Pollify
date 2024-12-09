package ru.coursework.pollify.mappings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.coursework.pollify.entity.Questionnaire;
import ru.coursework.pollify.service.QuestionnaireService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/questionnaires")
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @GetMapping("/{uri}")
    public String getQuestionnaire(@PathVariable String uri, Model model) {
        var questionnaire = questionnaireService.findQuestionnaireByUri(UUID.fromString(uri));
        if (questionnaire != null) {
            model.addAttribute("questionnaire", questionnaire);
            model.addAttribute("uri", uri);
            return "questionnaire";
        } else {
            return "redirect:/";
        }
    }
}
