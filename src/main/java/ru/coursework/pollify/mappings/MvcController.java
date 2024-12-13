package ru.coursework.pollify.mappings;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.coursework.pollify.dto.QuestionnaireCredentialsDTO;
import ru.coursework.pollify.dto.QuestionnaireDTO;
import ru.coursework.pollify.entity.Questionnaire;
import ru.coursework.pollify.repository.QuestionRepository;
import ru.coursework.pollify.repository.QuestionnaireRepository;
import ru.coursework.pollify.service.TokenUtil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class MvcController {


    private final TokenUtil tokenUtil;
    private final QuestionnaireRepository questionnaireRepository;
    private final QuestionRepository questionRepository;

    @GetMapping("/questionnaire/edit")
    public ModelAndView editQuestionnaire(HttpSession session) {
        QuestionnaireCredentialsDTO credentials = (QuestionnaireCredentialsDTO) session.getAttribute("credentials");

        if (credentials == null) {
            return new ModelAndView("redirect:/error");
        }

        var questionnaire = questionnaireRepository.findByUri(credentials.uri());

        if (questionnaire == null || !tokenUtil.isTokenValid(credentials.accessToken(), questionnaire.getAccessToken())) {
            return new ModelAndView("redirect:/error");
        }

        return new ModelAndView("editQuestionnaire")
                .addObject("questionnaire", questionnaire)
                .addObject("credentials", credentials);
    }

    @GetMapping("/api/questionnaire/edit")
    public ModelAndView editQuestionnaireApi(@ModelAttribute QuestionnaireCredentialsDTO credentialsDTO, HttpSession session) {
        session.setAttribute("credentials", credentialsDTO);
        return new ModelAndView("redirect:/questionnaire/edit");
    }

    @GetMapping("/api/questionnaire/create")
    public ModelAndView createQuestionnaireApi(@ModelAttribute QuestionnaireDTO questionnaireDTO, HttpSession session) {
        var token = UUID.randomUUID().toString();
        var questionnaire = Questionnaire.builder()
                .is_private(questionnaireDTO.is_private())
                .title(questionnaireDTO.title())
                .description(questionnaireDTO.description())
                .accessToken(tokenUtil.hash(token))
                .uri(UUID.randomUUID().toString())
                .build();

        questionnaire = questionnaireRepository.save(questionnaire);

        var credentials = new QuestionnaireCredentialsDTO(questionnaire.getUri(), token, questionnaire.getId());
        session.setAttribute("credentials", credentials);

        return new ModelAndView("redirect:/questionnaire/edit");
    }

    @GetMapping("/questionnaire/pass")
    public ModelAndView passQuestionnaire(@ModelAttribute String uri, HttpSession session) {

        var questionnaire = questionnaireRepository.findByUri(uri);
        if (questionnaire == null) {
            return new ModelAndView("redirect:/error");
        }

        return new ModelAndView("passQuestionnaire")
                .addObject("questionnaire", questionnaire);
    }
}
