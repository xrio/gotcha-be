package codeit.gatcha.API.client.controller;

import codeit.gatcha.API.client.service.security.API_SignUpService;
import codeit.gatcha.API.client.DTO.APIResponse;
import codeit.gatcha.domain.user.DTO.SignUpDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController @RequiredArgsConstructor
public class SignUpController {
    private final API_SignUpService signUpService;

    @PostMapping("/signup")
    public ResponseEntity<APIResponse> signUp(@RequestBody @Validated SignUpDTO signUpDTO){
        return signUpService.signUpAndSendConfirmationEmail(signUpDTO);
    }

    @GetMapping(value="/confirm-account")
    public ResponseEntity<APIResponse> confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
        return signUpService.confirmUserAccount(confirmationToken);
    }

}
