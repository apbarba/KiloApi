package com.salesianostriana.dam.kiloapi.dto.Destinatario;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.Caja.CajaViews;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DestinatarioDto {

    @JsonView({DestinatarioViews.Master.class, CajaViews.GetCaja.class})
    private Long id;
    @JsonView({DestinatarioViews.Master.class, CajaViews.GetCaja.class})
    private String nombre;
    @JsonView(DestinatarioViews.Master.class)
    private String direccion;
    @JsonView(DestinatarioViews.Master.class)
    private String personaContacto;
    @JsonView(DestinatarioViews.Master.class)
    private String telefono;
    @JsonView({DestinatarioViews.Master.class, DestinatarioViews.Simple.class})
    private Double kgTotales;
    @JsonView(DestinatarioViews.Master.class)
    private List<Integer> numCajas;

    @JsonView(DestinatarioViews.Simple.class)
    private Long numeroCajas;


    public DestinatarioDto(Long id, String nombre, String direccion, String personaContacto, String telefono, Double kgTotales) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.personaContacto = personaContacto;
        this.telefono = telefono;
        this.kgTotales = kgTotales;
    }

    public DestinatarioDto(Double kgTotales, Long numeroCajas) {
        this.kgTotales = kgTotales;
        this.numeroCajas = numeroCajas;
    }

    public DestinatarioDto(Long id, String nombre, String direccion, String personaContacto, String telefono){
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.personaContacto = personaContacto;
        this.telefono = telefono;
    }


}