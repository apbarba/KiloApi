package com.salesianostriana.dam.kiloapi.dto;

import com.salesianostriana.dam.kiloapi.model.Aportacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AportacionDto {

    private Long id;
    private String nombreCurso;
    private String tutor;
    private LocalDate fecha;
    private Map<String, Double> aportaciones = new HashMap<>();

    public static AportacionDto of(Aportacion a) {
        Map<String, Double> list = new HashMap<>();
        a.getDetalleAportacionList().forEach(d -> {
            list.put(d.getTipoAlimento().getNombre(), d.getCantidadKg());
        });

        return AportacionDto.builder()
                .id(a.getId())
                .nombreCurso(a.getClase().getNombre())
                .tutor(a.getClase().getTutor())
                .fecha(a.getFecha())
                .aportaciones(list)
                .build();
    }

}
