package com.salesianostriana.dam.kiloapi.dto.Aportacion.DetalleAportacion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.Aportacion.AportacionViews;
import com.salesianostriana.dam.kiloapi.model.Aportacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    public static List<DetalleAportacionDto> of(Aportacion a) {
        List<DetalleAportacionDto> listado = new ArrayList<>();

        a.getDetalleAportacionList().forEach(d -> {
            listado.add(DetalleAportacionDto.builder()
                    .numLinea(d.getDetallesPK().getNumLinea_id())
                    .tipoAlimento(d.getTipoAlimento().getNombre())
                    .cantidadAportada(d.getCantidadKg())
                    .build());
        });

        return listado;

    }

}
