package com.salesianostriana.dam.kiloapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tiene {

    @EmbeddedId
    @Builder.Default
    private TienePK id = new TienePK();

    @ManyToOne
    @MapsId("tipoAlimento_id")
    @JoinColumn(name = "tipoAlimento_id")
    private TipoAlimento tipoAlimento;

    @ManyToOne
    @MapsId("caja_id")
    @JoinColumn(name = "caja_id")
    private Caja caja;

    private double cantidadKgs;


    public Tiene(TipoAlimento tipoAlimento, Caja caja) {
        this.tipoAlimento = tipoAlimento;
        this.caja = caja;
    }

}
