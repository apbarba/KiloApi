package com.salesianostriana.dam.kiloapi.dto.Caja;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.TipoAlimento.TipoAlimentoDto;
import com.salesianostriana.dam.kiloapi.model.Caja;
import com.salesianostriana.dam.kiloapi.model.Destinatario;
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
public class CajaDto {

    @JsonView(CajaViews.Master.class)
    private Long id;
    @JsonView(CajaViews.Master.class)
    private String qr;
    @JsonView(CajaViews.Master.class)
    private int numCaja;
    @JsonView(CajaViews.Master.class)
    private double kilosTotales;
    @JsonView(CajaViews.Master.class)
    private String destinatario;
    @JsonView(CajaViews.Master.class)
    private List<TipoAlimentoDto> alimentos;

    public static CajaDto of(Caja c) {
        List<TipoAlimentoDto> listado = new ArrayList<>();
        c.getTieneList().forEach(l -> {
            TipoAlimentoDto t = TipoAlimentoDto.builder()
                    .id(l.getTipoAlimento().getId())
                    .nombre(l.getTipoAlimento().getNombre())
                    .kilosEnviados(l.getCantidadKgs())
                    .kilosDisponibles(null)
                    .build();
            listado.add(t);
        });

        return CajaDto.builder()
                .id(c.getId())
                .qr(c.getQr())
                .numCaja(c.getNumCaja())
                .kilosTotales(c.getKilosTotales())
                .destinatario(c.getDestinatario().getNombre())
                .alimentos(listado)
                .build();
    }

}
