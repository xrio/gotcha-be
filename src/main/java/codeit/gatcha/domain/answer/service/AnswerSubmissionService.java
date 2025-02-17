package codeit.gatcha.domain.answer.service;

import codeit.gatcha.API.client.DTO.question.inputDTO.QuestionAnswer_DTO;
import codeit.gatcha.application.user.service.UserSessionService;
import codeit.gatcha.domain.answer.entity.Answer;
import codeit.gatcha.domain.answer.repo.AnswerRepo;
import codeit.gatcha.domain.question.entity.Question;
import codeit.gatcha.domain.question.repo.QuestionRepo;
import codeit.gatcha.domain.user.entity.GatchaUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;

@Service @RequiredArgsConstructor
public class AnswerSubmissionService {
    private final QuestionRepo questionRepo;
    private final UserSessionService userSessionService;
    private final AnswerRepo answerRepo;

    public void checkAndSubmitAnswer(QuestionAnswer_DTO answerDTO) {
        Question question = getQuestionOrThrow(answerDTO);
        GatchaUser currentLoggedInUser = userSessionService.getCurrentLoggedInUser();

        answerRepo.
                findByQuestionAndUser(question, currentLoggedInUser).
                ifPresentOrElse(answer -> updateExistingAnswer(answer, answerDTO.getAnswer()),
                                () -> createNewAnswer(answerDTO, question, currentLoggedInUser));
    }

    private Question getQuestionOrThrow(QuestionAnswer_DTO answerDTO) {
        return questionRepo.
                findById(answerDTO.getQuestionId()).
                orElseThrow(() -> new EntityNotFoundException(String.format("Question %s wasn't found", answerDTO.getQuestionId())));
    }

    private void updateExistingAnswer(Answer answer, String newBody){
        answer.setBody(newBody);
        answerRepo.save(answer);
    }

    private void createNewAnswer(QuestionAnswer_DTO answer_dto, Question question, GatchaUser currentLoggedInUser){
        answerRepo.save(new Answer(answer_dto, question, currentLoggedInUser));
    }




}
