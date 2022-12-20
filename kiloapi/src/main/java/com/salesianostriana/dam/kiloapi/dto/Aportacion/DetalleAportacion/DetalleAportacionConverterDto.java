package com.salesianostriana.dam.kiloapi.dto.Aportacion.DetalleAportacion;

import com.salesianostriana.dam.kiloapi.model.DetalleAportacion;

public class DetalleAportacionConverterDto {

    public DetalleAportacionDto detalleAportacionToDetalleAportacionDto(DetalleAportacion detalleAportacion){

        return DetalleAportacionDto.builder()
                .tipoAlimento(detalleAportacion.getTipoAlimento().getNombre())
                .cantidadAportada(detalleAportacion.getCantidadKg())
                .numLinea(detalleAportacion.getDetallesPK().getNumLinea_id())
                .kilosDisponibles(detalleAportacion.getTipoAlimento().getKilosDisponibles().getCantidadDisponible())
                .build();
    }
}
