package com.salesianostriana.dam.kiloapi.dto;

import com.salesianostriana.dam.kiloapi.model.Aportacion;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AportacionDtoConverter {

    /*
    public GetAportacionDto getAportacionDetallesDto(DetalleAportacion detalleAportacion){

        return GetAportacionDto.builder()
                .id(detalleAportacion.getId())
                .numLinea(detalleAportacion.getId())
                .cantidadK(detalleAportacion.getCantidadKilos())
                .build();
    }

     */

    public GetAportacionDto aportacionToGetAportacionDto(Aportacion aportacion, Map<String, Double> mapa){

        return GetAportacionDto.builder()
                .fecha(aportacion.getFecha())
                .listaAlimentos(mapa)
                .build();
    }


}
