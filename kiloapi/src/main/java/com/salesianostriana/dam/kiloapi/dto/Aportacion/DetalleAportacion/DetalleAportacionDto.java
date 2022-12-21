package com.salesianostriana.dam.kiloapi.dto.Aportacion.DetalleAportacion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.Aportacion.AportacionViews;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class DetalleAportacionDto {

    @JsonView(AportacionViews.Master.class)
    private long numLinea;
    @JsonView(AportacionViews.Master.class)
    private String tipoAlimento;
    @JsonView(AportacionViews.Master.class)
    private double cantidadAportada;

    private double kilosDisponibles;

    public DetalleAportacionDto(long numLinea, String tipoAlimento, double kilosDisponibles) {
        this.numLinea = numLinea;
        this.tipoAlimento = tipoAlimento;
        this.kilosDisponibles = kilosDisponibles;
    }

}
