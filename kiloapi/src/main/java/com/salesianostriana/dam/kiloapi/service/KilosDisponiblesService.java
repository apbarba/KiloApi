package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.model.*;
import com.salesianostriana.dam.kiloapi.repository.KilosDisponiblesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KilosDisponiblesService {

    private final KilosDisponiblesRepository kilosDisponiblesRepository;

    public KilosDisponibles add(KilosDisponibles kilosDisponibles) {
        return kilosDisponiblesRepository.save(kilosDisponibles);
    }

    public Optional<KilosDisponibles> findById(TipoAlimento tipoAlimento) {
        return kilosDisponiblesRepository.findById(tipoAlimento);
    }

    public List<KilosDisponibles> findAll() {
        return kilosDisponiblesRepository.findAll();
    }

    public KilosDisponibles edit(KilosDisponibles kilosDisponibles) {
        return kilosDisponiblesRepository.save(kilosDisponibles);
    }

    public void delete(KilosDisponibles kilosDisponibles) {
        kilosDisponiblesRepository.delete(kilosDisponibles);
    }

    public void deleteById(TipoAlimento tipoAlimento) {
        kilosDisponiblesRepository.deleteById(tipoAlimento);
    }

    public void devolverKilos(Caja c) {
        // Coger de la caja, la lista de "tiene", compararla con la lista de todos los kilosdisponibles, y si coinciden,
        // sumar la cantidadKgs a los kilosdisponibles de ese tipo
        List<KilosDisponibles> listado = this.findAll();
        List<Tiene> listadoTiene = c.getTieneList();

        if(!listadoTiene.isEmpty()) {
            listado.forEach(k -> {
                listadoTiene.forEach(t -> {
                    if (t.getTipoAlimento() == k.getTipoAlimento()) {
                        k.setCantidadDisponible(k.getCantidadDisponible() + t.getCantidadKgs());
                    }
                });
            });
        }
    }

    public void restarKilos(DetalleAportacion da) {
        this.findAll().forEach(k -> {
            if (k.getTipoAlimento().equals(da.getTipoAlimento())) {
                k.setCantidadDisponible(k.getCantidadDisponible() - da.getCantidadKg());
                this.edit(k);
            }
        });
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