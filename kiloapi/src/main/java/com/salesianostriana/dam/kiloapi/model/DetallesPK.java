package com.salesianostriana.dam.kiloapi.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@Data
public class DetallesPK implements Serializable {

    private static final long serialVersionUID = 2L;
    private long id_aportacion;
    private long numLinea_id;

    public DetallesPK(long id_aportacion, long numLinea_id){
        this.id_aportacion = id_aportacion;
        this.numLinea_id = numLinea_id;
    }
}