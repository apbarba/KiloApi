package com.salesianostriana.dam.kiloapi.dto;

import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoAlimentoDto {

    private Long id;
    private String nombre;
    private double kilosDisponibles;


    public static TipoAlimentoDto of(TipoAlimento t) {
        return TipoAlimentoDto.builder()
                .id(t.getId())
                .nombre(t.getNombre())
                .kilosDisponibles(t.getKilosDisponibles().getCantidadDisponible())
                .build();
    }

}
