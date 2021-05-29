package ru.iu3.rpo.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.iu3.rpo.backend.models.Artist;
import ru.iu3.rpo.backend.models.Painting;
import ru.iu3.rpo.backend.repositories.PaintingRepository;
import ru.iu3.rpo.backend.tools.DataValidationException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1")
public class PaintingController {
    @Autowired
    PaintingRepository paintingRepository;


    @GetMapping("/paintings")
    public Page<Painting> getAllPaintings(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return paintingRepository.findAll(PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "name")));
    }


    @GetMapping("/paintings/{id}")
    public ResponseEntity<Painting> getPainting(@PathVariable(value = "id") Long paintingId)
            throws DataValidationException
    {
        Painting painting = paintingRepository.findById(paintingId)
                .orElseThrow(()-> new DataValidationException("Картина с таким индексом не найдена"));
        return ResponseEntity.ok(painting);
    }

    @PostMapping("/paintings")
    public ResponseEntity<Object> createPainting(@Valid @RequestBody Painting painting) throws DataValidationException {
        try {
            @Valid Painting nc = paintingRepository.save(painting);
            return new ResponseEntity<Object>(nc, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            String error;
            if (ex.getMessage().contains("paintings.name_UNIQUE"))
                throw new DataValidationException("Данная картина уже есть в базе");
            else
                throw new DataValidationException("Неизвестная ошибка");
        }
    }


    @PutMapping("/paintings/{id}")
    public ResponseEntity<Painting> updatePainting(@PathVariable(value = "id") Long paintingId,
                                               @Valid @RequestBody Painting paintingDetails) {
        Painting painting = null;
        Optional<Painting> cc = paintingRepository.findById(paintingId);
        if (cc.isPresent())
        {
            painting = cc.get();
            painting.name = paintingDetails.name;
            painting.year = paintingDetails.year;
            painting.artist = paintingDetails.artist;
            painting.museum = paintingDetails.museum;
            paintingRepository.save(painting);
        }
        else
        {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "paining not found"
            );
        }
        return ResponseEntity.ok(painting);
    }

    @DeleteMapping("/paintings/{id}")
    public Map<String, Boolean> deletePainting(@PathVariable(value = "id") Long paintingId) {
        Optional<Painting> artist = paintingRepository.findById(paintingId);
        Map<String, Boolean> response = new HashMap<>();
        if (artist.isPresent())
        {
            paintingRepository.delete(artist.get());
            response.put("deleted", Boolean.TRUE);
        }
        else
            response.put("deleted", Boolean.FALSE);
        return response;
    }
}
