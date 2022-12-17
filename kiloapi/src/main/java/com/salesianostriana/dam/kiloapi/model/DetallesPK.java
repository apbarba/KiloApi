package com.salesianostriana.dam.kiloapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor @NoArgsConstructor
@Getter
@Setter
public class DetallesPK implements Serializable {

    private long id_aportacion;
    private long numLinea_id;
}
