package ru.coursework.pollify.mappings;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.coursework.pollify.dto.QuestionnaireDTO;
import ru.coursework.pollify.entity.Questionnaire;
import ru.coursework.pollify.repository.QuestionnaireRepository;
import ru.coursework.pollify.service.TokenUtil;

import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class QuestionnaireController {


    private final TokenUtil tokenUtil;
    private final QuestionnaireRepository questionnaireRepository;

    @PostMapping("/api/questionnaire/create")
    public ModelAndView createQuestionnaire(@ModelAttribute QuestionnaireDTO questionnaireDTO) {
        var questionnaire = Questionnaire.builder()
                .is_private(questionnaireDTO.is_private())
                .title(questionnaireDTO.title())
                .description(questionnaireDTO.description())
                .accessToken(tokenUtil.hash(UUID.randomUUID().toString()))
                .uri(UUID.randomUUID().toString())
                .build();
        questionnaire = questionnaireRepository.save(questionnaire);
        return new ModelAndView("redirect:/questionnaire/edit/" + questionnaire.getUri());
    }

    @GetMapping("/questionnaire/edit/{uri}")
    public ModelAndView editQuestionnaire(@PathVariable String uri) {
        Questionnaire questionnaire = questionnaireRepository.findByUri(uri);
        if (questionnaire == null) {
            return new ModelAndView("redirect:/error");
        }
        return new ModelAndView("editQuestionnaire")
                .addObject("questionnaire", questionnaire);
    }



}
