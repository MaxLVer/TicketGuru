package com.melkeinkood.ticket_guru.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
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

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class PostinumeroController {

    @Autowired
    private PostinumeroRepository postinumeroRepo;

    private EntityModel<PostinumeroDTO> toEntityModel(PostinumeroDTO postinumeroDTO) {
        Link selfLink = linkTo(methodOn(PostinumeroController.class).haePostinumero(postinumeroDTO.getPostinumeroId()))
                .withSelfRel();
        return EntityModel.of(postinumeroDTO, selfLink);
    }

    private PostinumeroDTO toDTO(Postinumero postinumero) {
        return new PostinumeroDTO(
                postinumero.getPostinumeroId(),
                postinumero.getPostinumero(),
                postinumero.getKaupunki());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @GetMapping("/postinumerot")
    public ResponseEntity<List<EntityModel<PostinumeroDTO>>> getPostinumerot() {
        List<Postinumero> postinumerot = postinumeroRepo.findAll();
        List<EntityModel<PostinumeroDTO>> postinumeroModels = postinumerot.stream()
                .map(postinumero -> toEntityModel(toDTO(postinumero)))
                .collect(Collectors.toList());

        if (postinumeroModels.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(postinumeroModels);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SALESPERSON')")
    @GetMapping("/postinumerot/{id}")
    public ResponseEntity<?> haePostinumero(@PathVariable Long id) {
        Optional<Postinumero> optionalPostinumero = postinumeroRepo.findById(id);
        if (optionalPostinumero.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Postinumeroa ei löydy"));
        }
        return ResponseEntity.ok(toEntityModel(toDTO(optionalPostinumero.get())));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/postinumerot")
    public ResponseEntity<?> lisaaPostinumero(@Valid @RequestBody PostinumeroDTO postinumeroDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        if (postinumeroDTO.getPostinumero().isEmpty() || postinumeroDTO.getPostinumero() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ("Postinumero ei voi olla tyhjä")));
        }

        if (postinumeroDTO.getKaupunki().isEmpty() || postinumeroDTO.getKaupunki() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", ("Kaupunki ei voi olla tyhjä")));
        }

        Postinumero uusiPostinumero = new Postinumero(
                postinumeroDTO.getPostinumero(),
                postinumeroDTO.getKaupunki());

        Postinumero savedPostinumero = postinumeroRepo.save(uusiPostinumero);

        return ResponseEntity.status(HttpStatus.CREATED).body(toEntityModel(toDTO(savedPostinumero)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/postinumerot/{id}")
    public ResponseEntity<?> muokkaaPostinumero(@Valid @PathVariable Long id,
            @RequestBody PostinumeroDTO postinumeroDTO) {

        Optional<Postinumero> optionalPostinumero = postinumeroRepo.findById(id);

        if (optionalPostinumero.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Postinumeroa ei löydy"));
        }

        Postinumero postinumero = optionalPostinumero.get();

        postinumero.setPostinumero(postinumeroDTO.getPostinumero());

        postinumero.setKaupunki(postinumeroDTO.getKaupunki());

        Postinumero updatedPostinumero = postinumeroRepo.save(postinumero);
        return ResponseEntity.ok(toEntityModel(toDTO(updatedPostinumero)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/postinumerot/{id}")
    public ResponseEntity<String> deletePostinumero(@PathVariable Long id) {
        if (!postinumeroRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: Postinumeroa ei löydetty");
        }

        postinumeroRepo.deleteById(id);
        return ResponseEntity.ok("Postinumero " + id + " on poistettu");
    }
}
