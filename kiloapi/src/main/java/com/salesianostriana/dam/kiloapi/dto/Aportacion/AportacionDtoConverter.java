package com.salesianostriana.dam.kiloapi.dto.Aportacion;
import com.salesianostriana.dam.kiloapi.model.Aportacion;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class AportacionDtoConverter {

    public AportacionDto aportacionToGetAportacionDto(Aportacion aportacion, Map<String, Double> mapa){
        return AportacionDto.builder()
                .fecha(aportacion.getFecha())
                .listaAlimentos(mapa)
                .build();
    }
}