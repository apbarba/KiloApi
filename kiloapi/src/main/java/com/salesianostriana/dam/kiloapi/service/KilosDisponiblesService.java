package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KilosDisponiblesService {

    public void devolverKilos(Caja c) {
        List<KilosDisponibles> listado = this.findAll();
        List<Tiene> listadoTiene = c.getTieneList();

        if (!listadoTiene.isEmpty()) {
            listado.forEach(k -> {
                listadoTiene.forEach(t -> {
                    if (t.getTipoAlimento() == k.getTipoAlimento()) {
                        k.setCantidadDisponible(k.getCantidadDisponible() + t.getCantidadKgs());
                    }
                });
            });
        }
    }

    public void modificarKilos(DetalleAportacion da, int opcion, double cantPrev) {
        switch (opcion) {
            case 1:
                this.findAll().forEach(k -> {
                    if (k.getTipoAlimento().equals(da.getTipoAlimento())) {
                        k.setCantidadDisponible(k.getCantidadDisponible() - da.getCantidadKg());
                        this.edit(k);
                    }
                });
                break;
            case 2:
                this.findAll().forEach(k -> {
                    if (k.getTipoAlimento().equals(da.getTipoAlimento())) {
                        k.setCantidadDisponible(k.getCantidadDisponible() - cantPrev + da.getCantidadKg());
                        this.edit(k);
                    }
                });
                break;
            default:
                break;
        }
    }

    public void agregarKilos(Map<TipoAlimento, Double> aportacion) {
        List<KilosDisponibles> listado = this.findAll();

        listado.forEach(l -> {
            aportacion.keySet().forEach(tipoAlimento -> {
                if (l.getTipoAlimento() == tipoAlimento) {
                    l.setCantidadDisponible(l.getCantidadDisponible() + aportacion.get(tipoAlimento));
                    this.edit(l);
                }
            });
        });
    }

}
