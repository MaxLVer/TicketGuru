package com.melkeinkood.ticket_guru.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.melkeinkood.ticket_guru.model.Postinumero;
import com.melkeinkood.ticket_guru.model.dto.PostinumeroDTO;
import com.melkeinkood.ticket_guru.repositories.PostinumeroRepository;

@RestController
public class PostinumeroController {

    @Autowired
    private PostinumeroRepository postinumeroRepo;

    private PostinumeroDTO toDTO(Postinumero postinumero) {
        return new PostinumeroDTO(
                postinumero.getPostinumeroId(),
                postinumero.getPostinumero(),
                postinumero.getKaupunki()
        );
    }

    @GetMapping("/postinumerot")
    public List<PostinumeroDTO> getPostinumerot() {
        return postinumeroRepo.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/postinumerot/{id}")
    public ResponseEntity<PostinumeroDTO> haePostinumero(@PathVariable Long id) {
        Postinumero postinumero = postinumeroRepo.findById(id).orElse(null);
        return (postinumero != null) ? 
            ResponseEntity.ok(toDTO(postinumero)) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/postinumerot")
    public ResponseEntity<PostinumeroDTO> lisaaPostinumero(@RequestBody PostinumeroDTO postinumeroDTO) {

        Postinumero uusiPostinumero = new Postinumero(
                postinumeroDTO.getPostinumero(),
                postinumeroDTO.getKaupunki());

        Postinumero savedPostinumero = postinumeroRepo.save(uusiPostinumero);

        PostinumeroDTO responseDTO = toDTO(savedPostinumero);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/postinumerot/{id}")
    public ResponseEntity<?> muokkaaPostinumero(
        @PathVariable Long id,
        @RequestBody PostinumeroDTO postinumeroDTO) {
    
    Optional<Postinumero> optionalPostinumero = postinumeroRepo.findById(id);

    if (optionalPostinumero.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error: Postinumero with id " + id + " not found.");
    }

    Postinumero postinumero = optionalPostinumero.get();

    postinumero.setPostinumero(postinumeroDTO.getPostinumero());

    postinumero.setKaupunki(postinumeroDTO.getKaupunki());

    postinumeroRepo.save(postinumero);

    return ResponseEntity.ok("Postinumero updated successfully.");
}

     @DeleteMapping("/postinumerot/{id}")
     public ResponseEntity<String> deletePostinumero(@PathVariable Long id) {
        if (!postinumeroRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: Postinumero with id " + id + " not found.");
        }
    
        postinumeroRepo.deleteById(id);
        return ResponseEntity.ok("Postinumero with id " + id + " deleted successfully.");
    }
    }

