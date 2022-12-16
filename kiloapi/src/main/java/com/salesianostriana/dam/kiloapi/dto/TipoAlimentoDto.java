package com.salesianostriana.dam.kiloapi.dto;

import com.salesianostriana.dam.kiloapi.model.Tiene;
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
    private double kilosAportados;
    private double kilosDisponibles;


    public static TipoAlimentoDto of(TipoAlimento t) {
        return TipoAlimentoDto.builder()
                .id(t.getId())
                .nombre(t.getNombre())
                .kilosDisponibles(t.getKilosDisponibles().getCantidadDisponible())
                .build();
    }

    public static TipoAlimentoDto mostrarDetallesTipoAlimento(TipoAlimento tipo, Tiene tiene) {
        return TipoAlimentoDto.builder()
                .id(tipo.getId())
                .nombre(tipo.getNombre())
                .kilosAportados(tiene.getCantidadKgs())
                .kilosDisponibles(tipo.getKilosDisponibles().getCantidadDisponible())
                .build();
    }

}
