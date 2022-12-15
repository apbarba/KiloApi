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
public class Caja {

    @Id
    @GeneratedValue
    private Long id;

    private String qr;

    private Integer numCajas;

    private Double kilosTotales;


}
