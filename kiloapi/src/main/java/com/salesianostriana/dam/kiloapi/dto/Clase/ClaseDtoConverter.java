package com.salesianostriana.dam.kiloapi.dto.Clase;

import com.salesianostriana.dam.kiloapi.model.Clase;
import com.salesianostriana.dam.kiloapi.model.Destinatario;
import org.springframework.stereotype.Component;

@Component
public class ClaseDtoConverter {

    public Clase createClaseDtoToClase(ClaseDto createClase, Destinatario d){

        return  Clase.builder()
                .id(d.getId())
                .nombre(createClase.getNombre())
                .tutor(createClase.getTutor())
                .build();


        }

    public ClaseDto claseToGetClaseDto(Clase clase){

        return ClaseDto
                .builder()
                .nombre(clase.getNombre())
                .id(clase.getId())
                .numAportaciones((long) clase.getAportacionList().size())
                //.totalKilos()
                .build();
    }
}
