package com.salesianostriana.dam.kiloapi.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TienePK implements Serializable {

    //???
    private static final long serialVersionUID = 8682909319466153524L;

    private Long tipoAlimento_id;
    private Long caja_id;

}