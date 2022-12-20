package com.salesianostriana.dam.kiloapi.dto.Clase;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ClaseDto {

    private Long idAportacion;

    @JsonView(ClaseViews.Master.class)
    private String nombre;

    private String tutor;
    @JsonView(ClaseViews.Master.class)
    private Double totalKilos;
    @JsonView(ClaseViews.Master.class)
    private Long numAportaciones;
    @JsonView(ClaseViews.Master.class)
    private Double media;



    public ClaseDto(String nombre, Long numAportaciones,Double totalKilos,  Double media) {
        this.nombre = nombre;
        this.numAportaciones = numAportaciones;
        this.totalKilos = totalKilos;
        this.media = media;
    }
}
