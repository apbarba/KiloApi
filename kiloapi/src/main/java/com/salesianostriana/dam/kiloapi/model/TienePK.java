package com.salesianostriana.dam.kiloapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@Embeddable
public class TienePK {

    //???
    private static final long serialVersionUID = 8682909319466153524L;

    long tipoAlimento_id;
    long caja_id;

}
