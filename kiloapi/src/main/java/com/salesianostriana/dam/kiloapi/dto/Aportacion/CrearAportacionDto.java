package com.salesianostriana.dam.kiloapi.dto.Aportacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrearAportacionDto {

    private LocalDate fecha;
    private Long id_clase;
    private Map<Long, Double> detalle = new HashMap<>();

}
