package com.salesianostriana.dam.kiloapi.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Tiene {

    @EmbeddedId
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tiene tiene = (Tiene) o;
        return id.equals(tiene.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "Tiene{" +
                //"tipoAlimento=" + tipoAlimento +
                ", cantidadKgs=" + cantidadKgs +
                '}';
    }

}
