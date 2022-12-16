package com.salesianostriana.dam.kiloapi.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAportacion {

    private Long id;

    private Long numLinea;

    private double cantidadK;
}
