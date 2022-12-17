package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.dto.CajaDto;
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

    public Caja addKilostoCaja(Caja caja, Long id, Long idTipoAlim, Double cantidad) {
        TipoAlimento tp = tipoAlimentoService.findById(idTipoAlim).get();
        Tiene tiene = tieneService.findById(new TienePK(tp.getId(), caja.getId())).get();

        if (tp.getKilosDisponibles().getCantidadDisponible() >= cantidad) {
            caja.setKilosTotales(tiene.getCantidadKgs() + cantidad);
            tp.getKilosDisponibles().setCantidadDisponible(tp.getKilosDisponibles()
                    .getCantidadDisponible() - cantidad);
        }
        return repository.save(caja);
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
