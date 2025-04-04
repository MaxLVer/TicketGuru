package com.melkeinkood.ticket_guru.auth.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.melkeinkood.ticket_guru.model.Kayttaja;
import com.melkeinkood.ticket_guru.model.Rooli;


public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;

    //kokoelma myönnettyjä valtuuksia rooleja/käyttöoikeuksia
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Kayttaja kayttaja) {
        this.username = kayttaja.getKayttajanimi();
        this.password= kayttaja.getSalasana();
        List<GrantedAuthority> auths = new ArrayList<>();

        Rooli rooli = kayttaja.getRooli();  
        
        //jos rooli on olemassa niin lisää valtuuksia
        if (rooli != null && rooli.getNimike() != null) {
            auths.add(new SimpleGrantedAuthority(rooli.getNimike().toUpperCase()));
        }
        
        this.authorities = auths;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}