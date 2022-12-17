package com.salesianostriana.dam.kiloapi.dto;

import lombok.*;
import org.springframework.data.util.Pair;

import java.time.LocalDate;
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

    private String fecha;

    private List<Pair<String,Integer>> alimentos;
}
