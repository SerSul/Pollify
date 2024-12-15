package ru.coursework.pollify.mappings;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.coursework.pollify.dto.QuestionAnswerDTO;
import ru.coursework.pollify.dto.QuestionnaireCredentialsDTO;
import ru.coursework.pollify.dto.QuestionnaireDTO;
import ru.coursework.pollify.entity.Questionnaire;
import ru.coursework.pollify.entity.QuestionnaireAnswer;
import ru.coursework.pollify.entity.QuestionnaireQuestionAnswer;
import ru.coursework.pollify.repository.QuestionRepository;
import ru.coursework.pollify.repository.QuestionnaireAnswerRepository;
import ru.coursework.pollify.repository.QuestionnaireQuestionAnswerRepository;
import ru.coursework.pollify.repository.QuestionnaireRepository;
import ru.coursework.pollify.service.TokenUtil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class MvcController {


    private final TokenUtil tokenUtil;
    private final QuestionnaireRepository questionnaireRepository;
    private final QuestionRepository questionRepository;
    private final QuestionnaireQuestionAnswerRepository questionAnswerRepository;
    private final QuestionnaireAnswerRepository questionnaireAnswerRepository;

    /**
     * Отображает страницу редактирования анкеты.
     *
     * @param session HTTP-сессия, используемая для хранения данных о пользователе.
     * @return ModelAndView объект, содержащий информацию для отображения страницы редактирования анкеты.
     */
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
                .addObject("questionnaire", questionnaire)
                .addObject("credentials", credentials);
    }

    /**
     * Обрабатывает запрос на редактирование анкеты через API.
     *
     * @param credentialsDTO DTO объект с данными учетных данных анкеты.
     * @param session HTTP-сессия, используемая для хранения данных о пользователе.
     * @return ModelAndView объект, перенаправляющий на страницу редактирования анкеты.
     */
    @GetMapping("/api/questionnaire/edit")
    public ModelAndView editQuestionnaireApi(@ModelAttribute QuestionnaireCredentialsDTO credentialsDTO, HttpSession session) {
        session.setAttribute("credentials", credentialsDTO);
        return new ModelAndView("redirect:/questionnaire/edit");
    }

    /**
     * Обрабатывает запрос на создание новой анкеты через API.
     *
     * @param questionnaireDTO DTO объект с данными новой анкеты.
     * @param session HTTP-сессия, используемая для хранения данных о пользователе.
     * @return ModelAndView объект, перенаправляющий на страницу редактирования новой анкеты.
     */
    @GetMapping("/api/questionnaire/create")
    public ModelAndView createQuestionnaireApi(@ModelAttribute QuestionnaireDTO questionnaireDTO, HttpSession session) {
        var token = UUID.randomUUID().toString();
        var questionnaire = Questionnaire.builder()
                .title(questionnaireDTO.title())
                .accessToken(tokenUtil.hash(token))
                .uri(UUID.randomUUID().toString())
                .build();

        questionnaire = questionnaireRepository.save(questionnaire);

        var credentials = new QuestionnaireCredentialsDTO(questionnaire.getUri(), token, questionnaire.getId());
        session.setAttribute("credentials", credentials);

        return new ModelAndView("redirect:/questionnaire/edit");
    }

    /**
     * Отображает страницу прохождения анкеты.
     *
     * @param session HTTP-сессия, используемая для хранения данных о пользователе.
     * @return ModelAndView объект, содержащий информацию для отображения страницы прохождения анкеты.
     */
    @GetMapping("/questionnaire/pass")
    public ModelAndView passQuestionnaire(HttpSession session) {
        var uri = session.getAttribute("uri").toString();
        var questionnaire = questionnaireRepository.findByUri(uri);
        if (questionnaire == null) {
            return new ModelAndView("redirect:/error");
        }

        return new ModelAndView("passQuestionnaire")
                .addObject("questionnaire", questionnaire);
    }

    /**
     * Обрабатывает запрос на прохождение анкеты через API.
     *
     * @param uri URI анкеты, которую необходимо пройти.
     * @param session HTTP-сессия, используемая для хранения данных о пользователе.
     * @return ModelAndView объект, перенаправляющий на страницу прохождения анкеты.
     */
    @GetMapping("/api/questionnaire/pass")
    public ModelAndView passQuestionnaireApi(@RequestParam String uri, HttpSession session) {
        if (uri == null) {
            return new ModelAndView("redirect:/error");
        }
        session.setAttribute("uri", uri);

        return new ModelAndView("redirect:/questionnaire/pass");
    }

    /**
     * Обрабатывает отправку ответов на анкету.
     *
     * @param answers Карта с ответами на вопросы анкеты.
     * @param session HTTP-сессия, используемая для хранения данных о пользователе.
     * @return ModelAndView объект, перенаправляющий на главную страницу после успешной отправки.
     */
    @PostMapping("/api/questionnaire/submit")
    public ModelAndView submitQuestionnaire(@RequestParam Map<String, String> answers, HttpSession session) {
        var questionnaireId = answers.get("id");
        answers.remove("id");

        var questionnaire = questionnaireRepository.findById(Long.valueOf(questionnaireId)).orElse(null);
        if (questionnaire == null) {
            return new ModelAndView("redirect:/error");
        }
        var questionnaireAnswer = QuestionnaireAnswer.builder()
                .questionnaire(questionnaire)
                .date(new Date())
                .build();
        questionnaireAnswer = questionnaireAnswerRepository.save(questionnaireAnswer);

        for (Map.Entry<String, String> entry : answers.entrySet()) {
            String questionId = entry.getKey();
            String answer = entry.getValue();

            var question = questionRepository.findById(Long.valueOf(questionId)).orElse(null);
            if (question == null) {return new ModelAndView("redirect:/error"); }

            var ans = QuestionnaireQuestionAnswer.builder()
                    .question(question)
                    .answer(answer)
                    .questionnaireAnswer(questionnaireAnswer)
                    .build();

            questionAnswerRepository.save(ans);
        }

        return new ModelAndView("redirect:/home");
    }






}
