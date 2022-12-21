package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.model.Caja;
import com.salesianostriana.dam.kiloapi.model.Tiene;
import com.salesianostriana.dam.kiloapi.model.TienePK;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import com.salesianostriana.dam.kiloapi.repository.CajaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CajaServiceLogica {

    private final CajaRepository repository;
    private final TipoAlimentoService tipoAlimentoService;

    private final TieneService tieneService;

    public Caja addKilostoCaja(Caja caja, Long idTipoAlim, double cantidad) {
        Optional<TipoAlimento> tp = tipoAlimentoService.findById(idTipoAlim);
        if (tp.isPresent()) {
            TipoAlimento tipoAl = tp.get();
            Optional<Tiene> ti = tieneService.findById(new TienePK(tipoAl.getId(), caja.getId()));
            if (ti.isPresent()) {
                Tiene tiene = ti.get();
                caja.setKilosTotales(caja.getKilosTotales() + cantidad);
                tiene.setCantidadKgs(tiene.getCantidadKgs() + cantidad);
                tipoAl.getKilosDisponibles().setCantidadDisponible(tipoAl.getKilosDisponibles()
                        .getCantidadDisponible() - cantidad);
            } else {
                Tiene ttp = Tiene
                        .builder()
                        .id(new TienePK(tipoAl.getId(), caja.getId()))
                        .build();
                tipoAl.addTipoToTiene(ttp);
                caja.addTieneToCaja(ttp);
                this.agregarTipoYKilos(caja, tipoAl, ttp, cantidad);
            }
        }
        return repository.save(caja);
    }

    public boolean comprobarCantidad(Long idTipoAlim, double cantidad) {

        Optional<TipoAlimento> tipoAl = tipoAlimentoService.findById(idTipoAlim);
        if (tipoAl.isPresent()) {
            TipoAlimento tp = tipoAl.get();
            return tp.getKilosDisponibles().getCantidadDisponible() >= cantidad
                    && tp.getKilosDisponibles().getCantidadDisponible() > 0;
        }
        return false;
    }
    public void agregarTipoYKilos(Caja caja, TipoAlimento tipoAlimento, Tiene tiene, double cantidad) {
        caja.setKilosTotales(caja.getKilosTotales() + cantidad);
        tiene.setCantidadKgs(tiene.getCantidadKgs() + cantidad);
        tipoAlimento.getKilosDisponibles().setCantidadDisponible(tipoAlimento.getKilosDisponibles()
                .getCantidadDisponible() - cantidad);
    }
}