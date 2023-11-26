package br.com.hebio.desafiofd.service;

import br.com.hebio.desafiofd.exception.EmailAlreadyExistsException;
import br.com.hebio.desafiofd.exception.PasswordConfirmationException;
import br.com.hebio.desafiofd.exception.UserNotFoundException;
import br.com.hebio.desafiofd.model.User;
import br.com.hebio.desafiofd.model.UserRecord;
import br.com.hebio.desafiofd.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Page<User> listAll(Pageable pagination) {
        return userRepository.findAll(pagination);
    }

    public User saveUser(UserRecord userRecord) {
        verifyIfEmailAlreadyExtists(userRecord.email());
        verifyPassword(userRecord.password(), userRecord.passwordConfirmation());
        User user = new User(userRecord.name(), userRecord.email(), userRecord.password());
        return userRepository.save(user);
    }

    public void verifyIfEmailAlreadyExtists(String email) {
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

    public User updateUser(Long userId, UserRecord userRecord) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.getEmail().equals(userRecord.email())) {
            verifyIfEmailAlreadyExtists(userRecord.email());
        }

        verifyPassword(userRecord.password(), userRecord.passwordConfirmation());
        user.setName(userRecord.name());
        user.setEmail(userRecord.email());
        user.setPassword(userRecord.password());
        return userRepository.save(user);
    }
}
