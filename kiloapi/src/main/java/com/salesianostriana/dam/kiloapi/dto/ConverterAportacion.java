package com.salesianostriana.dam.kiloapi.dto;

import com.salesianostriana.dam.kiloapi.model.Aportacion;
import com.salesianostriana.dam.kiloapi.model.DetalleAportacion;
import org.springframework.stereotype.Component;

@Component
public class ConverterAportacion {

    public GetAportacion getAportacionDetallesDto(Aportacion aportacion, DetalleAportacion detalleAportacion){

        return GetAportacion.builder()
                .id(detalleAportacion.getId())
                .numLinea(detalleAportacion.getId())
                .cantidadK(detalleAportacion.getCantidadKilos())
                .build();
    }
}
