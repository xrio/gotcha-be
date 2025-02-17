package codeit.gatcha.API.client.DTO.question.outputDTO;

import codeit.gatcha.domain.question.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data @NoArgsConstructor @AllArgsConstructor
public class Admin_QuestionDTO {
    @NotBlank(message = "Question can't be empty")
    private String body;
    @NotBlank(message = "Question id can't be null")
    private Integer id;

    public Admin_QuestionDTO(Question question){
        this.body = question.getBody();
        this.id = question.getId();
    }
}
