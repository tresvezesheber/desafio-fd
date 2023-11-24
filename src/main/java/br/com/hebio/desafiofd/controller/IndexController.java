package br.com.hebio.desafiofd.controller;

import br.com.hebio.desafiofd.model.User;
import br.com.hebio.desafiofd.model.UserRecord;
import br.com.hebio.desafiofd.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500", maxAge = 3600)
@AllArgsConstructor
@RestController
@RequestMapping("/")
public class IndexController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> usersList = userService.listAll();
        return ResponseEntity.ok(usersList);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserRecord userRecord) {
        User userSaved = userService.saveUser(userRecord);
        return ResponseEntity.ok(userSaved);
    }


}
