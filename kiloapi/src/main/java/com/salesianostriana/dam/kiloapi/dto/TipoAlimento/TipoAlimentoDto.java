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
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TipoAlimentoDto {

    @JsonView({TipoAlimentoViews.Master.class, CajaViews.PostCajaTipo.class})
    private Long id;
    @JsonView({TipoAlimentoViews.Master.class, CajaViews.PostCajaTipo.class})
    private String nombre;
    @JsonView({TipoAlimentoViews.MostrarDisponible.class, CajaViews.PostCajaTipo.class})
    private Double kilosDisponibles;
    @JsonView(TipoAlimentoViews.MostrarEnviado.class)
    private double kilosEnviados;

    public TipoAlimentoDto(Long id, String nombre, double kilosDisponibles){
        this.id = id;
        this.nombre = nombre;
        this.kilosDisponibles = kilosDisponibles;
    }

}
