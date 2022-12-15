package com.salesianostriana.dam.kiloapi.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
public class DetalleAportacion {


    @ManyToOne
    @JoinColumn(name = "aportaciones", foreignKey = @ForeignKey(name = "FK_APORTACION_ID"))
    private Aportacion aportaciones;
    @Id
    @GeneratedValue
    private Long numLinea;

    private Double cantidadKgs;
}
