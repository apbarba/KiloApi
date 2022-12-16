package com.salesianostriana.dam.kiloapi.dto;

import com.salesianostriana.dam.kiloapi.model.Caja;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCajaDto {

    private String qr;
    private int numCaja;

    public static Caja createCajaDtoToCaja(CreateCajaDto c) {
        return Caja.builder()
                .qr(c.getQr())
                .numCaja(c.getNumCaja())
                .build();
    }

}
