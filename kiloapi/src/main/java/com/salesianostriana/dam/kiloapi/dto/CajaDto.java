package com.salesianostriana.dam.kiloapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    private Long id;
    private String qr;
    private int numCaja;
    private double kilosTotales;
    private Destinatario destinatario;
    private List<TipoAlimentoDto> alimentos;

    public static CajaDto mostrarDetallesCaja(Caja c) {
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
                .destinatario(c.getDestinatario())
                .alimentos(listado)
                .build();
    }

}
