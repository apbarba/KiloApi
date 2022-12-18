package com.salesianostriana.dam.kiloapi.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAportacionDto {

    private Long id;

    private Long numLinea;

    private Integer cantidadK;

    private LocalDate fecha;

    @Builder.Default
    private Map<String,Double> listaAlimentos= new HashMap<String, Double>();

    /*
    private List<AbstractMap.SimpleEntry<String,Integer>> alimentos;

    //Constructor para que funcione correctamente
    public GetAportacionDto(LocalDate fecha, List<AbstractMap.SimpleEntry<String, Integer>> alimentos){

        this.fecha = fecha;

        this.alimentos = alimentos;
    }
    */

    }


