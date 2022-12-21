package com.salesianostriana.dam.kiloapi.dto.Clase;

import com.salesianostriana.dam.kiloapi.model.Clase;
import org.springframework.stereotype.Component;

@Component
public class ClaseDtoConverter {

    public ClaseDto claseToGetClaseDto(Clase clase){

        return ClaseDto
                .builder()
                .nombre(clase.getNombre())
                .id(clase.getId())
                .tutor(clase.getTutor())
                .numAportaciones((long) clase.getAportacionList().size())
                .build();
    }
}
