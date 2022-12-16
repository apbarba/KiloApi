package com.salesianostriana.dam.kiloapi.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DetalleAportacion {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aportacion_id")
    private Aportacion aportacion;

    private double cantidadKilos;


}
