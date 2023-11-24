package br.com.hebio.desafiofd.service;

import br.com.hebio.desafiofd.exception.EmailAlreadyExistsException;
import br.com.hebio.desafiofd.exception.PasswordConfirmationException;
import br.com.hebio.desafiofd.model.User;
import br.com.hebio.desafiofd.model.UserRecord;
import br.com.hebio.desafiofd.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public User saveUser(UserRecord userRecord) {
        verifyIfUserAlreadyExtists(userRecord.email());
        verifyPassword(userRecord.password(), userRecord.passwordConfirmation());
        User user = new User(userRecord.name(), userRecord.email(), userRecord.password());
        return userRepository.save(user);
    }

    public void verifyIfUserAlreadyExtists(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new EmailAlreadyExistsException("This email already exists");
                });
    }


    public void verifyPassword(String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation)) {
            throw new PasswordConfirmationException("The Passwords must be equals");
        }
    }

}
