package br.com.hebio.desafiofd;

import br.com.hebio.desafiofd.model.User;
import br.com.hebio.desafiofd.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/")
public class IndexController {

    private final UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> usersList = userRepository.findAll();
        return ResponseEntity.ok(usersList);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
        User userSaved = userRepository.save(user);
        return ResponseEntity.ok(userSaved);
    }


}
