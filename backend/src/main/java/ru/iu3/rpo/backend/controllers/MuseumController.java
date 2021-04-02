package ru.iu3.rpo.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.iu3.rpo.backend.models.Country;
import ru.iu3.rpo.backend.models.Museum;
import ru.iu3.rpo.backend.models.User;
import ru.iu3.rpo.backend.repositories.MuseumRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class MuseumController {
    @Autowired
    MuseumRepository museumRepository;

    @GetMapping("/museums")
    // getAllMuseums возвращает список музеев, который будет автоматически преобразован в JSON
    public List<Museum> getAllMuseums() {
        return museumRepository.findAll();
    }

    @PostMapping("/museums")
    public ResponseEntity<Object> createMuseum(@Valid @RequestBody Museum museum) {
        Museum nc = museumRepository.save(museum);
        return ResponseEntity.ok(nc);
    }
}
