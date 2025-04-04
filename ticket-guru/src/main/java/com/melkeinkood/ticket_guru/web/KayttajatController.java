package com.melkeinkood.ticket_guru.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.HttpHeaders;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import com.melkeinkood.ticket_guru.auth.dto.JwtResponseDTO;
import com.melkeinkood.ticket_guru.auth.dto.LoginRequestDTO;
import com.melkeinkood.ticket_guru.auth.model.RefreshToken;
import com.melkeinkood.ticket_guru.auth.services.JwtService;
import com.melkeinkood.ticket_guru.auth.services.RefreshTokenService;
import com.melkeinkood.ticket_guru.model.Kayttaja;
import com.melkeinkood.ticket_guru.model.Rooli;
import com.melkeinkood.ticket_guru.repositories.KayttajaRepository;
import com.melkeinkood.ticket_guru.repositories.RooliRepository;
import com.melkeinkood.ticket_guru.model.dto.KayttajaDTO;

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

    @Autowired
    private RefreshTokenService refreshTokenService;

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
        Optional<Kayttaja> existingUser = kayttajaRepo.findByKayttajanimi(kayttajaDTO.getKayttajanimi());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(Map.of("error", "Username already exists"));
        }
        //Salasanan hash ennen tallentamista
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
   public JwtResponseDTO AuthenticateAndGetToken(@RequestBody LoginRequestDTO authRequestDTO, HttpServletResponse response){
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getKayttajanimi(), authRequestDTO.getSalasana()));
    if(authentication.isAuthenticated()){
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getKayttajanimi());
        String accessToken = jwtService.generateToken(authRequestDTO.getKayttajanimi());
        //aseta tokeni headeriin
        ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(jwtService.getJwtExpirationInMS() / 1000)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return JwtResponseDTO.builder()
                .accessToken(accessToken)
                .token(refreshToken.getToken()).build();

    } else {
        throw new UsernameNotFoundException("invalid user request..!!");
    }

}

@PostMapping("/kayttajat/uloskirjaudu")
public ResponseEntity<Void> logout(HttpServletResponse response) {

    // Poista token laittamalla expiration aika 0
    ResponseCookie deleteAccessTokenCookie = ResponseCookie.from("accessToken", "")
            .httpOnly(true)
            .secure(false) //Laita false jos local dev
            .path("/")
            .maxAge(0)
            .sameSite("Strict")
            .build();

    response.setHeader(HttpHeaders.SET_COOKIE, deleteAccessTokenCookie.toString());

    return ResponseEntity.noContent().build();
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
