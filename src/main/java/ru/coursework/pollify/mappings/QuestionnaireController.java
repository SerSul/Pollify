package ru.coursework.pollify.mappings;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class QuestionnaireController {


    private final TokenUtil tokenUtil;
    private final QuestionnaireRepository questionnaireRepository;
    private final QuestionRepository questionRepository;

    @PostMapping("/api/questionnaire/question/add")
    public ResponseEntity<Void> addQuestion(@RequestBody AddQuestionDTO addQuestionDTO) {
        if (addQuestionDTO.questionnaireId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        var questionnaire = questionnaireRepository.findById(addQuestionDTO.questionnaireId()).orElse(null);
        if (questionnaire == null) {
            return ResponseEntity.notFound().build();
        }

        String questionText = addQuestionDTO.questionText();
        if (questionText == null || questionText.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        var question = QuestionnaireQuestion.builder()
                .questionnaire(questionnaire)
                .text(questionText)
                .build();
        try {
            questionRepository.save(question);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/api/questionnaire/question/delete/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
