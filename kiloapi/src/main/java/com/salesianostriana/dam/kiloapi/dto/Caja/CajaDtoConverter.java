package com.salesianostriana.dam.kiloapi.dto.Caja;

import com.salesianostriana.dam.kiloapi.model.Caja;
import com.salesianostriana.dam.kiloapi.model.Destinatario;
import org.springframework.stereotype.Component;

@Component
public class CajaDtoConverter {

    public CajaDto cajaToGetCajaDto (Caja caja){
        return CajaDto.builder()
                .id(caja.getId())
                .numCaja(caja.getNumCaja())
                .qr(caja.getQr())
                .kilosTotales(caja.getKilosTotales())
                .destinatario(caja.getDestinatario().getNombre())
                .build();
    }

    public Caja of (CajaDto cajaDto) {
        return new Caja(
                cajaDto.getId(),
                cajaDto.getQr(),
                cajaDto.getNumCaja()
        );
    }
}