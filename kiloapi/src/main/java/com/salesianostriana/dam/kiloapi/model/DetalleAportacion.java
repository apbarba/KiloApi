package com.salesianostriana.dam.kiloapi.model;

<<<<<<< HEAD

=======
>>>>>>> origin/mario_entidades
import lombok.*;

import javax.persistence.*;

@Entity
<<<<<<< HEAD
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
=======
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DetalleAportacion {

    @EmbeddedId
    private DetallesPK detallesPK;

    //    @MapsId("numLinea_id")
    private int numLinea;

    private double cantidadKg;

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
>>>>>>> origin/mario_entidades
