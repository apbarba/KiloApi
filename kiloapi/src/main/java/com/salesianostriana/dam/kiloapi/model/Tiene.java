package com.salesianostriana.dam.kiloapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

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

    //HELPERS arturo
    public void addTipoAlimento(TipoAlimento tipoAlimento) {
        this.tipoAlimento = tipoAlimento;
        if (tipoAlimento.getTieneList() == null)
            tipoAlimento.setTieneList(new ArrayList<>());
        tipoAlimento.getTieneList().add(this);
    }

    public void removeTipoAlimento(TipoAlimento tipoAlimento) {
        tipoAlimento.getTieneList().remove(this);
        this.tipoAlimento = null;
    }

    public void addCaja(Caja caja) {
        this.caja = caja;
        if (caja.getTieneList() == null)
            caja.setTieneList(new ArrayList<>());
        caja.getTieneList().add(this);
    }

    public void removeCaja(Caja caja) {
        caja.getTieneList().remove(this);
        this.caja = null;
    }

    public void removeCajaYAlimento(Caja caja, TipoAlimento tipoAlimento) {
        removeCaja(caja);
        removeTipoAlimento(tipoAlimento);

    }
}