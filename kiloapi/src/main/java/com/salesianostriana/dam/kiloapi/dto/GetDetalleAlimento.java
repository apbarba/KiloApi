package com.salesianostriana.dam.kiloapi.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDetalleAlimento {

    private String nombre;

    private double kg;
}
