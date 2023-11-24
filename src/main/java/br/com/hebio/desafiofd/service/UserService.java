package br.com.hebio.desafiofd.service;

import br.com.hebio.desafiofd.exception.EmailAlreadyExistsException;
import br.com.hebio.desafiofd.model.User;
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

    public User saveUser(User user) {
        verifyIfUserAlreadyExtists(user.getEmail());
        return userRepository.save(user);
    }

    public void verifyIfUserAlreadyExtists(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new EmailAlreadyExistsException("This email already exists");
                });
    }


}
