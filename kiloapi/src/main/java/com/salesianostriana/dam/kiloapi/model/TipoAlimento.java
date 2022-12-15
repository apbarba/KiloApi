package com.salesianostriana.dam.kiloapi.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class TipoAlimento {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "tipoAlimento", fetch = FetchType.EAGER)
    private List<Tiene> tieneList = new ArrayList<>();

    @OneToOne(mappedBy = "tipoAlimento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private KilosDisponibles kilosDisponibles;


    // HELPERS GESTIÓN TIPOALIMENTO-KILOSDISPONIBLES
    public void addKilosToTipo(KilosDisponibles kilosDisponibles) {
        kilosDisponibles.setId(this.getId());
        kilosDisponibles.setTipoAlimento(this);
        this.kilosDisponibles = kilosDisponibles;
    }

    public void removeKilosFromTipo() {
        this.kilosDisponibles.setId(null);
        this.kilosDisponibles.setTipoAlimento(this);
        this.kilosDisponibles = null;
    }

}
