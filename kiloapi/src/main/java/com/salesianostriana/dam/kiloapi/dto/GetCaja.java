package com.salesianostriana.dam.kiloapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetCaja {

    private Long id;

    private double numCajas;

    private double kilosT;

    private String qr;
}
