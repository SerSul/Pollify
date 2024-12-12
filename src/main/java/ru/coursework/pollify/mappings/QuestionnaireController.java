package ru.coursework.pollify.mappings;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.coursework.pollify.dto.AddQuestionDTO;
import ru.coursework.pollify.dto.QuestionnaireCredentialsDTO;
import ru.coursework.pollify.dto.QuestionnaireDTO;
import ru.coursework.pollify.entity.Questionnaire;
import ru.coursework.pollify.entity.QuestionnaireQuestion;
import ru.coursework.pollify.repository.QuestionRepository;
import ru.coursework.pollify.repository.QuestionnaireRepository;
import ru.coursework.pollify.service.TokenUtil;

import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class QuestionnaireController {


    private final TokenUtil tokenUtil;
    private final QuestionnaireRepository questionnaireRepository;
    private final QuestionRepository questionRepository;

    @PostMapping("/api/questionnaire/create")
    public ModelAndView createQuestionnaire(@ModelAttribute QuestionnaireDTO questionnaireDTO, HttpSession session) {
        var token = UUID.randomUUID().toString();
        var questionnaire = Questionnaire.builder()
                .is_private(questionnaireDTO.is_private())
                .title(questionnaireDTO.title())
                .description(questionnaireDTO.description())
                .accessToken(tokenUtil.hash(token))
                .uri(UUID.randomUUID().toString())
                .build();

        questionnaire = questionnaireRepository.save(questionnaire);

        session.setAttribute("credentials", new QuestionnaireCredentialsDTO(questionnaire.getUri(), token, questionnaire.getId()));
        session.setAttribute("justCreated", true);

        return new ModelAndView("redirect:/questionnaire/edit");
    }

    @PostMapping("/api/questionnaire/question/add")
    public ResponseEntity<QuestionnaireQuestion> addQuestion(@RequestBody AddQuestionDTO addQuestionDTO) {
        var questionnaire = questionnaireRepository.findById(addQuestionDTO.questionnaireId()).orElse(null);

        if (questionnaire == null) {
            return ResponseEntity.badRequest().body(null);
        }

        var question = QuestionnaireQuestion.builder()
                .questionnaire(questionnaire)
                .text(addQuestionDTO.questionText())
                .build();

        questionRepository.save(question);

        return ResponseEntity.ok(question);
    }



    @GetMapping("/questionnaire/edit")
    public ModelAndView editQuestionnaire(HttpSession session) {
        var credentials = (QuestionnaireCredentialsDTO) session.getAttribute("credentials");
        if (credentials == null) {
            return new ModelAndView("redirect:/error");
        }

        var questionnaire = questionnaireRepository.findByUri(credentials.uri());
        if (questionnaire == null || !tokenUtil.isTokenValid(credentials.accessToken(), questionnaire.getAccessToken())) {
            return new ModelAndView("redirect:/error");
        }

        return new ModelAndView("editQuestionnaire")
                .addObject("questionnaire", questionnaire);
    }

}
