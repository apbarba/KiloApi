package com.salesianostriana.dam.kiloapi.dto.Aportacion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.model.Aportacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class AportacionDto {

    @JsonView(AportacionViews.Master.class)
    private Long id;
    @JsonView(AportacionViews.Master.class)
    private String nombreCurso;
    @JsonView(AportacionViews.Master.class)
    private String tutor;
    @JsonView(AportacionViews.Master.class)
    private LocalDate fecha;
    @JsonView(AportacionViews.Master.class)
    private List<DetalleAportacionDto> aportaciones = new ArrayList<>();

    public static AportacionDto of(Aportacion a) {

        return AportacionDto.builder()
                .id(a.getId())
                .nombreCurso(a.getClase().getNombre())
                .tutor(a.getClase().getTutor())
                .fecha(a.getFecha())
                .aportaciones(DetalleAportacionDto.of(a))
                .build();
    }

}
