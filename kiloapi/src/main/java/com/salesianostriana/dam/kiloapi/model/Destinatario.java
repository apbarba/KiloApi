package com.salesianostriana.dam.kiloapi.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Destinatario {

    @Id
    @GeneratedValue

    private Long id;

    private String nombre;

    private String direccion;

    private String personaContacto;

    private String telefono;
}
