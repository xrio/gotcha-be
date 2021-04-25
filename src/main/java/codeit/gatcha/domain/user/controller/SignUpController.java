package codeit.gatcha.domain.user.controller;

import codeit.gatcha.application.global.DTO.APIResponse;
import codeit.gatcha.domain.user.DTO.SignUpDTO;
import codeit.gatcha.domain.user.service.signUp.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequiredArgsConstructor
public class SignUpController {
    private final SignUpService signUpService;

    @PostMapping("/signup")
    public ResponseEntity<APIResponse> signUp(@RequestBody SignUpDTO signUpDTO){
        return signUpService.signUpAndSendConfirmationEmail(signUpDTO);
    }

    @GetMapping(value="/confirm-account")
    public ResponseEntity<APIResponse> confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
        return signUpService.confirmUserAccount(confirmationToken);
    }

}
