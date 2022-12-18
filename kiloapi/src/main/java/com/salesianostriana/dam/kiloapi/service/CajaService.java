package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.model.*;
import com.salesianostriana.dam.kiloapi.repository.CajaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CajaService {

    private final CajaRepository repository;
    private final TipoAlimentoService tipoAlimentoService;

    private final TieneService tieneService;

    private final KilosDisponiblesService kilosDisponiblesService;

    public Caja add(Caja caja) {
        return repository.save(caja);
    }

    public Caja addKilostoCaja(Caja caja, Long id, Long idTipoAlim, double cantidad) {
        Optional<TipoAlimento> tp = tipoAlimentoService.findById(idTipoAlim);
        Optional<Caja> cj = Optional.of(caja);
        if (cj.isPresent() & tp.isPresent()) {
            TipoAlimento tipoAl = tp.get();
            Optional<Tiene> ti = tieneService.findById(new TienePK(tipoAl.getId(), caja.getId()));
            if (ti.isPresent()) {
                Tiene tiene = ti.get();
                caja.setKilosTotales(tiene.getCantidadKgs() + cantidad);
                tipoAl.getKilosDisponibles().setCantidadDisponible(tipoAl.getKilosDisponibles()
                        .getCantidadDisponible() - cantidad);
            }
        }
        return repository.save(caja);
    }

    public boolean comprobarCantidad(Long id, Long idTipoAlim, double cantidad) {

        Optional<TipoAlimento> tipoAl = tipoAlimentoService.findById(idTipoAlim);
        if (tipoAl.isPresent()) {
            TipoAlimento tp = tipoAl.get();
            return tp.getKilosDisponibles().getCantidadDisponible() >= cantidad;
        }
        return false;
    }

    public Optional<Caja> findById(Long id) {
        return repository.findById(id);
    }

    public List<Caja> findAll() {
        return repository.findAll();
    }

    public Caja edit(Caja caja) {
        return repository.save(caja);
    }

    public void delete(Caja caja) {
        repository.delete(caja);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean comprobarDatos(Caja caja) {
        return caja.getQr() == "" || caja.getNumCaja() <= 0;
    }

}
