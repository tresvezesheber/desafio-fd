package br.com.hebio.desafiofd.controller;

import br.com.hebio.desafiofd.model.User;
import br.com.hebio.desafiofd.model.UserRecord;
import br.com.hebio.desafiofd.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<Page<User>> listAllUsers(@PageableDefault(size = 6, sort = {"name"}) Pageable pagination) {
        Page<User> usersList = userService.listAll(pagination);
        return ResponseEntity.ok(usersList);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserRecord userRecord) {
        User userSaved = userService.saveUser(userRecord);
        return ResponseEntity.ok(userSaved);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody @Valid UserRecord userRecord) {
        User userUpdated = userService.updateUser(userId, userRecord);
        return ResponseEntity.ok(userUpdated);
    }


}
