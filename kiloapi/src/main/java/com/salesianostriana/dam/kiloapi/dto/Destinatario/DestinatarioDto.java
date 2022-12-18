package com.salesianostriana.dam.kiloapi.dto.Destinatario;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.model.Caja;
import com.salesianostriana.dam.kiloapi.model.Destinatario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DestinatarioDto {

    @JsonView(DestinatarioViews.Master.class)
    private Long id;
    @JsonView(DestinatarioViews.Master.class)
    private String nombre;
    @JsonView(DestinatarioViews.Master.class)
    private String direccion;
    @JsonView(DestinatarioViews.Master.class)
    private String personaContacto;
    @JsonView(DestinatarioViews.Master.class)
    private String telefono;
    @JsonView(DestinatarioViews.Master.class)
    private double kgTotales;
    @JsonView(DestinatarioViews.Master.class)
    private List<Integer> numCajas;

    private static double kg = 0;

    public static DestinatarioDto of(Destinatario d) {
        kg = 0;
        List<Integer> num = new ArrayList<>();
        List<Caja> listado = d.getCajaList();
        listado.forEach(l -> {
            kg += l.getKilosTotales();
            num.add(l.getNumCaja());
        });

        return DestinatarioDto.builder()
                .id(d.getId())
                .nombre(d.getNombre())
                .direccion(d.getDireccion())
                .personaContacto(d.getPersonaContacto())
                .telefono(d.getTelefono())
                .kgTotales(kg)
                .numCajas(num)
                .build();
    }

}
