package com.salesianostriana.dam.kiloapi.dtos;

import com.salesianostriana.dam.kiloapi.model.Aportacion;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AportacionDtoConverter {

    public GetAportacionDto aportacionToGetAportacionDto(Aportacion aportacion, Map<String, Double> mapa){
        return GetAportacionDto.builder()
                .fecha(aportacion.getFecha())
                .listaAlimentos(mapa)
                .build();
    }
}
