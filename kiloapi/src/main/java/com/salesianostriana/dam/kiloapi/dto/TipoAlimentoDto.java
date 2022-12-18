package com.salesianostriana.dam.kiloapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.cajaDto.CajaViews;
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
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TipoAlimentoDto {

    @JsonView(CajaViews.PostCajaTipo.class)
    private Long id;
    @JsonView(CajaViews.PostCajaTipo.class)
    private String nombre;
    private double kilosAportados;
    @JsonView(CajaViews.PostCajaTipo.class)
    private double kilosDisponibles;

    public TipoAlimentoDto(Long id, String nombre, double kilosDisponibles){
        this.id = id;
        this.nombre = nombre;
        this.kilosDisponibles = kilosDisponibles;
    }
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
