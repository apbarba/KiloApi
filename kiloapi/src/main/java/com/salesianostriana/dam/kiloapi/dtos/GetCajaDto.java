package com.salesianostriana.dam.kiloapi.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCajaDto {
    private String qr;

    private int numCaja;

    private double kilosTotales;

    private Long idDestinatario;

    private String nombreDestinatario;

}
