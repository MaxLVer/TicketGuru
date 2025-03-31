package com.melkeinkood.ticket_guru.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.melkeinkood.ticket_guru.auth.services.JwtService;
import com.melkeinkood.ticket_guru.model.Kayttaja;
import com.melkeinkood.ticket_guru.model.Rooli;
import com.melkeinkood.ticket_guru.repositories.KayttajaRepository;
import com.melkeinkood.ticket_guru.repositories.RooliRepository;
import com.melkeinkood.ticket_guru.model.dto.KayttajaDTO;
import com.melkeinkood.ticket_guru.model.dto.LoginRequestDTO;

import org.springframework.web.bind.annotation.PutMapping;



@RestController
public class KayttajatController {


    @Autowired
    private KayttajaRepository kayttajaRepo;

    @Autowired
    private RooliRepository rooliRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private EntityModel<KayttajaDTO> toEntityModel(KayttajaDTO kayttajaDTO) {
        Link selfLink = linkTo(
            methodOn(KayttajatController.class)
            .haeKayttaja(kayttajaDTO.getKayttajaId()))
            .withSelfRel();

        Link rooliLink = linkTo(
            methodOn(RooliController.class).haeRooli(kayttajaDTO.getRooliId()))
            .withRel("rooli");

            return EntityModel.of(kayttajaDTO, selfLink,rooliLink);
    }

    private KayttajaDTO toDTO(Kayttaja kayttaja) {
        return new KayttajaDTO(
            kayttaja.getKayttajaId(),
            kayttaja.getRooli().getRooliId(),
            kayttaja.getKayttajanimi(),
            kayttaja.getSalasana(),
            kayttaja.getEtunimi(),
            kayttaja.getSukunimi()
        );
    } 

    @GetMapping("/kayttajat")
    public ResponseEntity<List<EntityModel<KayttajaDTO>>> haeKaikkiKayttajat() {
        List<Kayttaja> kayttajat = kayttajaRepo.findAll();

        List<EntityModel<KayttajaDTO>> kayttajamodel = kayttajat.stream()
        .map(kayttaja -> toEntityModel (toDTO(kayttaja)))
        .collect(Collectors.toList());

        if(kayttajamodel.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(kayttajamodel);
    }
    

    @GetMapping("/kayttajat/{id}")
    public ResponseEntity<Object> haeKayttaja(@PathVariable Long id) {

        Optional<Kayttaja> optionalKayttaja = kayttajaRepo.findById(id);

        if(optionalKayttaja.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Collections.singletonMap("error", "Käyttäjää ei löytynyt"));
        }
        KayttajaDTO kayttajaDTO = toDTO(optionalKayttaja.get());
        EntityModel<KayttajaDTO> entityModel = toEntityModel(kayttajaDTO);
        return ResponseEntity.ok(entityModel);
        
    }


    @PostMapping("/kayttajat/luo")
    public ResponseEntity<?> lisaaKayttaja(@Valid @RequestBody KayttajaDTO kayttajaDTO, BindingResult bindingResult){

        if(bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        String hashedPassword = passwordEncoder.encode(kayttajaDTO.getSalasana());
       
        Optional<Rooli> rooliOptional = rooliRepo.findById(kayttajaDTO.getRooliId());
        if(rooliOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("error", "Roolia ei löydy"));
        }
        Rooli rooli = rooliOptional.get();

         Kayttaja uusiKayttaja = new Kayttaja(
            rooli,
            kayttajaDTO.getKayttajanimi(),
            hashedPassword,
            kayttajaDTO.getEtunimi(),
            kayttajaDTO.getSukunimi()
         );

         Kayttaja savedKayttaja = kayttajaRepo.save(uusiKayttaja);

         KayttajaDTO responseDTO = toDTO(savedKayttaja);

         return ResponseEntity.status(HttpStatus.CREATED).body(toEntityModel(responseDTO));
    }

    @PostMapping("/kayttajat/kirjaudu")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            System.out.println("Attempting to authenticate user: " + loginRequest.getKayttajanimi());
            String hashedPassword = passwordEncoder.encode(loginRequest.getSalasana());
            System.out.println("Password: " + hashedPassword);
    
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getKayttajanimi(), loginRequest.getSalasana())
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwtToken = jwtService.generateToken(loginRequest.getKayttajanimi());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("JWT Token: " + jwtToken);
        } catch (BadCredentialsException e) {
            // Log the exception
            System.out.println("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
    
 
    @PutMapping("kayttajat/{id}")
    public ResponseEntity<?> muokkaaKayttajaa (@PathVariable Long id,
    @Valid @RequestBody KayttajaDTO kayttajaDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(),error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        } 

        Optional<Kayttaja> optionalKayttaja = kayttajaRepo.findById(id);

        if(optionalKayttaja.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Kayttaja kayttaja = optionalKayttaja.get();
        if (kayttajaDTO.getRooliId() != null) {
            Optional<Rooli> optionalRooli = rooliRepo.findById(kayttajaDTO.getRooliId());
            if (optionalRooli.isEmpty()) {
                return ResponseEntity
                    .badRequest()
                    .body(Map.of("error","rooli ID " + kayttajaDTO.getRooliId() + " ei ole olemassa"));
            }
            kayttaja.setRooli(optionalRooli.get());
        }

        
        kayttaja.setKayttajanimi(kayttajaDTO.getKayttajanimi());
        kayttaja.setSalasana(kayttajaDTO.getSalasana());
        kayttaja.setEtunimi(kayttajaDTO.getEtunimi());
        kayttaja.setSukunimi(kayttajaDTO.getSukunimi());
        kayttajaRepo.save(kayttaja);
        
        return ResponseEntity.ok("Kayttäjä päivitetty");
    }
    

    @DeleteMapping("/kayttajat/{id}")
    public ResponseEntity<Object> poistakayttaja(@PathVariable Long id) {
        if (!kayttajaRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("error", "Käyttäjää ei löydy"));
        } 
        kayttajaRepo.deleteById(id);
        return ResponseEntity.ok("Käyttäjä " +id + " on poistettu.");
        
    }

    
}
