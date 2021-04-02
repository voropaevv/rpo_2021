package ru.iu3.rpo.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.iu3.rpo.backend.models.Artist;
import ru.iu3.rpo.backend.models.Country;
import ru.iu3.rpo.backend.repositories.ArtistRepository;
import ru.iu3.rpo.backend.repositories.CountryRepository;
import javax.validation.Valid;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class ArtistController {
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    CountryRepository countryRepository;

    @GetMapping("/artists")
    // getAllArtists возвращает список художников, который будет автоматически преобразован в JSON
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    @PostMapping("/artists")
    public ResponseEntity<Object> createArtist(@Valid @RequestBody Artist artist) {
        Optional<Country> cc = countryRepository.findById(artist.country.id);
        if (cc.isPresent())
            artist.country = cc.get();
        Artist nc = artistRepository.save(artist);
        return ResponseEntity.ok(nc);
    }


    @PutMapping("/artists/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable(value = "id") Long countryId,
                                                 @Valid @RequestBody Artist artistDetails) {
        Artist artist = null;
        Optional<Artist> cc = artistRepository.findById(countryId);
        if (cc.isPresent())
        {
            artist = cc.get();
            artist.name = artistDetails.name;
            artist.century = artistDetails.century;
            artist.country = artistDetails.country;
            artistRepository.save(artist);
        }
        else
        {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "artists not found"
            );
        }
        return ResponseEntity.ok(artist);
    }

    @DeleteMapping("/artists/{id}")
    public Map<String, Boolean> deleteArtist(@PathVariable(value = "id") Long countryId) {
        Optional<Artist> artist = artistRepository.findById(countryId);
        Map<String, Boolean> response = new HashMap<>();
        if (artist.isPresent())
        {
            artistRepository.delete(artist.get());
            response.put("deleted", Boolean.TRUE);
        }
        else
            response.put("deleted", Boolean.FALSE);
        return response;
    }
}
