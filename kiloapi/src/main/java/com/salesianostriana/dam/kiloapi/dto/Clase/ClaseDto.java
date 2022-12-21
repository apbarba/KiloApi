package com.salesianostriana.dam.kiloapi.dto.Clase;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClaseDto {

    private Long id;
    @JsonView({ClaseViews.Master.class, ClaseViews.createDto.class})
    private String nombre;
    @JsonView(ClaseViews.createDto.class)
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

    public ClaseDto(String nombre, String tutor, Double totalKilos, Long numAportaciones) {
        this.nombre = nombre;
        this.tutor = tutor;
        this.totalKilos = totalKilos;
        this.numAportaciones = numAportaciones;
    }
}
