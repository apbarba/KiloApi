package com.salesianostriana.dam.kiloapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@Embeddable
@Data
public class TienePK implements Serializable {

    private static final long serialVersionUID = 1L;

    long tipoAlimento_id;
    long caja_id;

    public TienePK(long tipoAlimento_id, long caja_id) {
        this.tipoAlimento_id = tipoAlimento_id;
        this.caja_id = caja_id;
    }

}
