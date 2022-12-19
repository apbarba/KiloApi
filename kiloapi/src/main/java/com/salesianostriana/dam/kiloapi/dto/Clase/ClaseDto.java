package com.salesianostriana.dam.kiloapi.dto.Clase;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ClaseDto {

    private Long idAportacion;

    private String nombre;

    private String tutor;

    private double totalKilos;
}
