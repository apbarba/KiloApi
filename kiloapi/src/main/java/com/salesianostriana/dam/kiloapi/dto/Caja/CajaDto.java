package com.salesianostriana.dam.kiloapi.dto.Caja;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.DestinatarioDto;
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
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CajaDto {

    @JsonView(CajaViews.PostCaja.class)
    private Long id;
    @JsonView(CajaViews.PostCaja.class)
    private String qr;
    @JsonView(CajaViews.PostCaja.class)
    private int numCaja;
    @JsonView(CajaViews.PostCaja.class)
    private double kilosTotales;
    @JsonView(CajaViews.PostCajaTipo.class)
    private Destinatario destinatario;
    @JsonView(CajaViews.PostCajaTipo.class)
    private List<TipoAlimentoDto> alimentos;
    @JsonView(CajaViews.GetCajaId.class)
    private DestinatarioDto destinatarioDto;

    public static CajaDto mostrarDetallesCaja(Caja c) {
        List<TipoAlimentoDto> listado = new ArrayList<>();
        c.getTieneList().forEach(l -> {
            TipoAlimentoDto t = TipoAlimentoDto.builder()
                    .id(l.getTipoAlimento().getId())
                    .nombre(l.getTipoAlimento().getNombre())
                    .kilosEnviados(l.getCantidadKgs())
                    .kilosDisponibles(l.getTipoAlimento().getKilosDisponibles().getCantidadDisponible())
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
    public static CajaDto of(Caja c) {
        List<TipoAlimentoDto> listado = new ArrayList<>();
        c.getTieneList().forEach(l -> {
            TipoAlimentoDto t = TipoAlimentoDto.builder()
                    .id(l.getTipoAlimento().getId())
                    .nombre(l.getTipoAlimento().getNombre())
                    .kilosDisponibles(l.getTipoAlimento().getKilosDisponibles().getCantidadDisponible())
                    .build();
            listado.add(t);
        });

        return CajaDto.builder()
                .id(c.getId())
                .qr(c.getQr())
                .numCaja(c.getNumCaja())
                .kilosTotales(c.getKilosTotales())
                .destinatarioDto(DestinatarioDto.mostrarIdYNombre(c.getDestinatario()))
                .alimentos(listado)
                .build();

    }
}
