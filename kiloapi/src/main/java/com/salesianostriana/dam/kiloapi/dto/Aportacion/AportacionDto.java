package com.salesianostriana.dam.kiloapi.dto.Aportacion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.Aportacion.DetalleAportacion.DetalleAportacionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class AportacionDto {

    @JsonView(AportacionViews.Master.class)
    private Long id;
    @JsonView({AportacionViews.Master.class, AportacionViews.FindAllAportaciones.class})
    private String nombreClase;
    @JsonView(AportacionViews.Master.class)
    private String tutor;
    @JsonView({AportacionViews.Master.class, AportacionViews.FindAllAportaciones.class})
    private LocalDate fecha;
    @JsonView(AportacionViews.Master.class)
    private Double cantidadKg;
    @JsonView({AportacionViews.FindAllAportaciones.class})
    private Double cantidadkgTotales;
    @JsonView(AportacionViews.Master.class)
    private List<DetalleAportacionDto> aportaciones = new ArrayList<>();
    @Builder.Default
    private Map<String,Double> listaAlimentos= new HashMap<String, Double>();

    public AportacionDto(Long id, String nombreCurso, String tutor, LocalDate fecha) {
        this.id = id;
        this.nombreClase = nombreCurso;
        this.tutor = tutor;
        this.fecha = fecha;
    }

    public AportacionDto(String nombreClase, LocalDate fecha, Double cantidadkgTotales) {
        this.nombreClase = nombreClase;
        this.fecha = fecha;
        this.cantidadkgTotales = cantidadkgTotales;
    }
}
