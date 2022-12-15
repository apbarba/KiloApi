package com.salesianostriana.dam.kiloapi.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Destinatario {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    private String direccion;

    private String personaContacto;

    private String telefono;

    @OneToMany(mappedBy = "destinatario", fetch = FetchType.EAGER)
    private List<Caja> cajaList = new ArrayList<>();
}
