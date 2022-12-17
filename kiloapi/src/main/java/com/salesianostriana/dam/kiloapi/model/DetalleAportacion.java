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

    @EmbeddedId
    private DetallesPK detallesPK;

    //    @MapsId("numLinea_id")
    private int numLinea;

    private Integer cantidadKg;

//    @ManyToOne
//    @MapsId("id_aportacion")
//    @JoinColumn(name = "aportacion_id",
//            foreignKey = @ForeignKey(name = "FK_DETALLE_APORTACION_APORTACION"))
//    private Aportacion aportacion;

    @ManyToOne
    @JoinColumn(name = "tipoAlimento_id",
            foreignKey = @ForeignKey(name = "FK_DETALLE_TIPO_ALIMENTO"))
    private TipoAlimento tipoAlimento;
}


