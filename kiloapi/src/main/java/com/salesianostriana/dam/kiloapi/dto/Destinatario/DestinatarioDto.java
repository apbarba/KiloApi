package com.salesianostriana.dam.kiloapi.dto.Destinatario;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.Caja.CajaDto;
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
    private Double kgTotales;
    private List<Integer> numCajas;

    //Array de enteros para saber el numero de las cajas asignadas del destinatario correspondiente
    private List<Caja> numCajasAsignadasDestinatario = new ArrayList<>();

    private List<CajaDto> cajas = new ArrayList<>();

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

    public DestinatarioDto(Long id, String nombre, String direccion, String personaContacto, String telefono){
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.personaContacto = personaContacto;
        this.telefono = telefono;
    }

    public static DestinatarioDto mostrarDatos(Destinatario d){
        return DestinatarioDto
                .builder()
                .id(d.getId())
                .nombre(d.getNombre())
                .direccion(d.getDireccion())
                .personaContacto(d.getPersonaContacto())
                .telefono(d.getTelefono())
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