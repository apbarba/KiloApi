package com.salesianostriana.dam.kiloapi.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateClase {

    private Long idAportacion;

    private String nombre;

    private String tutor;
}
