package com.salesianostriana.dam.kiloapi.dto;

import com.salesianostriana.dam.kiloapi.model.Caja;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetDestinatario {

    private String nombre;

    private String direccion;

    private String personaContacto;

    private String telefono;

    private double kilosT;

    //Array de enteros para saber el numero de las cajas asignadas del destinatario correspondiente
    private List<Caja> numCajasAsignadasDestinatario = new ArrayList<>();
}
