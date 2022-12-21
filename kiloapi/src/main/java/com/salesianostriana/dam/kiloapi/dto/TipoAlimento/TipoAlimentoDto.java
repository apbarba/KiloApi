package com.salesianostriana.dam.kiloapi.dto.TipoAlimento;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.Caja.CajaViews;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
//@AllArgsConstructor

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TipoAlimentoDto {

    @JsonView({TipoAlimentoViews.Master.class, CajaViews.Master.class})
    private Long id;
    @JsonView({TipoAlimentoViews.Master.class, CajaViews.Master.class})
    private String nombre;
    @JsonView({TipoAlimentoViews.MostrarDisponible.class, CajaViews.Master.class})
    private Double kilosDisponibles;
    @JsonView({TipoAlimentoViews.MostrarEnviado.class, CajaViews.Master.class})
    private Double kilosEnviados;

    public TipoAlimentoDto(Long id, String nombre, Double kilosDisponibles, Double aux) {
        this.id = id;
        this.nombre = nombre;
        this.kilosDisponibles = kilosDisponibles;
    }

    public TipoAlimentoDto(Long id, String nombre, Double kilosEnviados) {
        this.id = id;
        this.nombre = nombre;
        this.kilosEnviados = kilosEnviados;
    }

}
