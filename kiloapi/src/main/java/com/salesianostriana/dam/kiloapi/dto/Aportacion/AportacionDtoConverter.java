package com.salesianostriana.dam.kiloapi.dto.Aportacion;
import com.salesianostriana.dam.kiloapi.dto.Aportacion.DetalleAportacion.DetalleAportacionConverterDto;
import com.salesianostriana.dam.kiloapi.model.Aportacion;
import com.salesianostriana.dam.kiloapi.model.DetalleAportacion;
import com.salesianostriana.dam.kiloapi.model.DetallesPK;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AportacionDtoConverter {
    private final DetalleAportacionConverterDto detalleAportacionConverterDto;

    public AportacionDto aportacionToGetAportacionDto(Aportacion aportacion, Map<String, Double> mapa){
        return AportacionDto.builder()
                .fecha(aportacion.getFecha())
                .listaAlimentos(mapa)
                .build();
    }

//    public AportacionDto getAportacionDetallesDto(DetalleAportacion detalleAportacion){
//
//        return AportacionDto.builder()
//                .id(new DetallesPK(detalleAportacion.get))
//                .cantidadK(detalleAportacion.getCantidadKg())
//                .build();
//    }
public AportacionDto aportacionToGetAportacionDto2(Aportacion aportacion){

    return AportacionDto.builder()
            .id(aportacion.getId())
            .fecha(aportacion.getFecha())
            .aportaciones(aportacion.getDetalleAportacionList()
                    .stream()
                    .map(detalleAportacionConverterDto::detalleAportacionToDetalleAportacionDto)
                    .collect(Collectors.toList()))
            .build();
}
}