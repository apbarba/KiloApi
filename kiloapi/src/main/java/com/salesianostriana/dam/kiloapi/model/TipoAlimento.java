package com.salesianostriana.dam.kiloapi.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TipoAlimento {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    @OneToOne(mappedBy = "tipoAlimento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private KilosDisponibles kilosDisponibles;

    // HELPERS GESTIÓN TIPO-KILOS
    public void addKilosToTipo(KilosDisponibles kilosDisponibles) {
        kilosDisponibles.setId(this.getId());
        kilosDisponibles.setTipoAlimento(this);
        this.kilosDisponibles = kilosDisponibles;
    }

    public void removeKilosFromTipo() {
        this.kilosDisponibles.setId(null);
        this.kilosDisponibles.setTipoAlimento(null);
        this.kilosDisponibles = null;
    }

    public void addTipoToTiene(Tiene t) {
        t.setTipoAlimento(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoAlimento that = (TipoAlimento) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TipoAlimento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", kilosDisponibles=" + kilosDisponibles +
                '}';
    }


}
