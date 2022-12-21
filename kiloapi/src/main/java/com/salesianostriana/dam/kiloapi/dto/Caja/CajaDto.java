package com.salesianostriana.dam.kiloapi.dto.Caja;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.Destinatario.DestinatarioDto;
import com.salesianostriana.dam.kiloapi.dto.TipoAlimento.TipoAlimentoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CajaDto {

    @JsonView({CajaViews.Master.class, CajaViews.CrearCaja.class})
    private Long id;
    @JsonView({CajaViews.Master.class, CajaViews.CrearCaja.class})
    private String qr;
    @JsonView({CajaViews.Master.class, CajaViews.CrearCaja.class})
    private int numCaja;
    @JsonView({CajaViews.Master.class, CajaViews.PostCajaTipo.class})
    private Double kilosTotales;
    @JsonView(CajaViews.Master.class)
    private String destinatario;
    @JsonView({CajaViews.Master.class, CajaViews.PostCajaTipo.class})
    private List<TipoAlimentoDto> alimentos;
    @JsonView({CajaViews.Master.class, CajaViews.GetCaja.class})
    private DestinatarioDto destinatarioDto;


    public CajaDto(String qr, int numCaja, Double kilosTotales, Long id, String destinatario){
        this.qr = qr;
        this.numCaja = numCaja;
        this.kilosTotales = kilosTotales;
        this.id = id;
        this.destinatario = destinatario;
    }

    public CajaDto(Long id, String qr, int numCaja, double kilosTotales, String destinatario) {
        this.id = id;
        this.qr = qr;
        this.numCaja = numCaja;
        this.kilosTotales = kilosTotales;
        this.destinatario = destinatario;
    }
}