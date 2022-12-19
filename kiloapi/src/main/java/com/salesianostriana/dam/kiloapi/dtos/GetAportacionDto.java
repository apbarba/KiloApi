package com.salesianostriana.dam.kiloapi.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAportacionDto {

    private LocalDate fecha;
    private String nombre;

    private Double cantidadKg;

    @Builder.Default
    private Map<String,Double> listaAlimentos= new HashMap<String, Double>();

}
