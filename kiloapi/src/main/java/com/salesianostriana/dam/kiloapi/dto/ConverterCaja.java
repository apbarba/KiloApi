package com.salesianostriana.dam.kiloapi.dto;

import com.salesianostriana.dam.kiloapi.model.Caja;
import org.springframework.stereotype.Component;

@Component
public class ConverterCaja {

    public GetCaja getCaja(Caja caja){

        return GetCaja.builder()
        //        .qr(caja.getQr)
       //         .numCajas(caja.getNumCajas())
      //          .kilosT(caja.getKilosT)
                .build();

    }
}
