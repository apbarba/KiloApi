package com.salesianostriana.dam.kiloapi.dto;

import lombok.*;
import org.springframework.data.util.Pair;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAportacion {

    private Long id;

    private Long numLinea;

    private Integer cantidadK;

    private LocalDate fecha;

    private List<AbstractMap.SimpleEntry<String,Integer>> alimentos;

    //Constructor para que funcione correctamente
    public GetAportacion(LocalDate fecha, List<AbstractMap.SimpleEntry<String, Integer>> alimentos){

        this.fecha = fecha;

        this.alimentos = alimentos;
    }

    }


