package ru.iu3.rpo.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ru.iu3.rpo.backend.models.Museum;
import ru.iu3.rpo.backend.models.User;
import ru.iu3.rpo.backend.repositories.MuseumRepository;
import ru.iu3.rpo.backend.repositories.UserRepository;


import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("api/v1")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MuseumRepository museumRepository;

    @GetMapping("/users")
    // getAllUsers возвращает список  юзеров, который будет автоматически преобразован в JSON
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        Optional<User> cc = userRepository.findById(user.id);
        User nc = userRepository.save(user);
        return ResponseEntity.ok(nc);
    }

    @PostMapping("/users/{id}/addmuseums")
    public ResponseEntity<Object> addMuseums(@PathVariable(value = "id") Long userId,
                                             @Valid @RequestBody Set<Museum> museums) {
        Optional<User> uu = userRepository.findById(userId);
        int cnt = 0;
        if (uu.isPresent()) {
            User u = uu.get();
            for (Museum m : museums) {
                Optional<Museum> mm = museumRepository.findById(m.id);
                if (mm.isPresent()) {
                    u.addMuseum(mm.get());
                    cnt++;
                }
            }
            userRepository.save(u);
        }
        Map<String, String> response = new HashMap<>();
        response.put("count", String.valueOf(cnt));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users/{id}/removemuseums")
    public ResponseEntity<Object> removeMuseums(@PathVariable(value = "id") Long userId,
                                             @Valid @RequestBody Set<Museum> museums) {
        Optional<User> uu = userRepository.findById(userId);
        int cnt = 0;
        if (uu.isPresent()) {
            User u = uu.get();
            for (Museum m : u.museums) {
                u.removeMuseum(m);
                cnt++;
            }
            userRepository.save(u);
        }
        Map<String, String> response = new HashMap<>();
        response.put("count", String.valueOf(cnt));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
                                           @Valid @RequestBody User userDetails) {
        User user = null;
        Optional<User> cc = userRepository.findById(userId);
        if (cc.isPresent())
        {
            user = cc.get();
            user.login = userDetails.login;
            user.email = userDetails.email;
            userRepository.save(user);
        }
        else
        {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "user not found"
            );
        }
        return ResponseEntity.ok(user);
    }

}
