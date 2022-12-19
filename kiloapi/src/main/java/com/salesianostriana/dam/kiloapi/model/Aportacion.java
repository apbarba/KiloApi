package com.salesianostriana.dam.kiloapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Aportacion {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "clase_id",
            foreignKey = @ForeignKey(name = "FK_APORTACION_CLASE"))
    private Clase clase;

    @JsonIgnore
    @OneToMany(mappedBy = "aportacion",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<DetalleAportacion> detalleAportacionList = new ArrayList<>();


    // HELPERS DE GESTIÓN APORTACIÓN-DETALLEAPORTACIÓN
    public void addDetalleAportacion(DetalleAportacion detalleAportacion) {
        detalleAportacion.setAportacion(this);
        this.detalleAportacionList.add(detalleAportacion);
    }

    public void removeDetalleAportacion(DetalleAportacion detalleAportacion) {
        this.detalleAportacionList.remove(detalleAportacion);
        detalleAportacion.setAportacion(null);
    }


    // HELPERS GESTIÓN APORTACIÓN-CLASE
    public void addAportacionToClase(Clase clase) {
        this.clase = clase;
        clase.getAportacionList().add(this);
    }

    public void removeAportacionFromClase(Clase clase) {
        clase.getAportacionList().remove(this);
        this.clase = null;
    }
}