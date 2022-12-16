package com.salesianostriana.dam.kiloapi.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetClase {

    private Long id;

    private String nombre;

    private Integer numAportaciones;

    private Double totalKilos;
}
