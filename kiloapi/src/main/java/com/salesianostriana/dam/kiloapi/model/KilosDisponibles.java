package com.salesianostriana.dam.kiloapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class KilosDisponibles {

    @Id
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_alimento")
    @MapsId
    private TipoAlimento tipoAlimento;

    private double cantidadDisponible;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KilosDisponibles that = (KilosDisponibles) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "KilosDisponibles{" +
                "id=" + id +
                ", cantidadDisponible=" + cantidadDisponible +
                '}';
    }

}