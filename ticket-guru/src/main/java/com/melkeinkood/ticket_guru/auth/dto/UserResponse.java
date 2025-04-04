package com.melkeinkood.ticket_guru.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

import com.melkeinkood.ticket_guru.model.Rooli;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
    private Long kayttajaId;
    private String username;
    private Set<Rooli> roles;
}