package com.salesianostriana.dam.kiloapi.model;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class Caja {

    @Id
    @GeneratedValue
    private Long id;

    private String qr;

    private int numCaja;

    // Se generan sumando de la lista de Tiene
    private double kilosTotales;

}
