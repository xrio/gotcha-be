package codeit.gatcha.domain.user.service.signUp;

import codeit.gatcha.application.security.repo.AuthorityRepo;
import codeit.gatcha.application.security.repo.ConfirmationTokenRepo;
import codeit.gatcha.domain.user.DTO.SignUpDTO;
import codeit.gatcha.domain.user.entity.User;
import codeit.gatcha.domain.user.repo.UserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @AllArgsConstructor @NoArgsConstructor
public class SignUpService {
    private UserRepo userRepo;
    private AuthorityRepo authorityRepo;
    private ConfirmationTokenRepo confirmationTokenRepo;

    public User createNewUser(SignUpDTO signUpDTO) {
        User newUser = createUserFromSignUpDTO(signUpDTO);
        return userRepo.save(newUser);
    }

    private User createUserFromSignUpDTO(SignUpDTO signUpDTO) {
        return User.
                builder().
                email(signUpDTO.getEmail()).
                password(signUpDTO.getPassword()).
                authority(authorityRepo.findByRole("ROLE_USER")).
                enabled(false).
                build();
    }

    public void activateUserAccount(String confirmationTokenString) {
        confirmationTokenRepo.
                findByConfirmationToken(confirmationTokenString).
                ifPresent(ct -> enableUserAccount(ct.getUser()));
    }

    private void enableUserAccount(User user) {
        user.setEnabled(true);
        userRepo.save(user);
    }
}
