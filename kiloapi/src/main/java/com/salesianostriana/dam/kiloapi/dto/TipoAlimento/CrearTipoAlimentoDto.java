package com.salesianostriana.dam.kiloapi.dto.TipoAlimento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrearTipoAlimentoDto {

    private String nombre;
    private double cantidadDisponible;

}
