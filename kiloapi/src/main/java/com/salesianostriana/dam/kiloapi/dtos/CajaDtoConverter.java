package com.salesianostriana.dam.kiloapi.dtos;

import com.salesianostriana.dam.kiloapi.model.Caja;
import com.salesianostriana.dam.kiloapi.model.Destinatario;
import org.springframework.stereotype.Component;

@Component
public class CajaDtoConverter {

    public GetCajaDto cajaToGetCajaDto (Caja caja){
        return GetCajaDto.builder()
                .numCaja(caja.getNumCaja())
                .qr(caja.getQr())
                .kilosTotales(caja.getKilosTotales())
                .idDestinatario(caja.getDestinatario().getId())
                .nombreDestinatario(caja.getDestinatario().getNombre())
                .build();
    }
}
