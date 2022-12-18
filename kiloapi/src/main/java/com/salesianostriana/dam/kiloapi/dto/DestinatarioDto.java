package com.salesianostriana.dam.kiloapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class DestinatarioDto {

    private Long id;
    private String nombre;
    private String direccion;
    private String personaContacto;
    private String telefono;
    private double kgTotales;
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

    public static DestinatarioDto mostrarIdYNombre(Destinatario d) {
        if (d != null) {
            return DestinatarioDto
                    .builder()
                    .id(d.getId())
                    .nombre(d.getNombre())
                    .build();
        }
        return null;
    }

}
