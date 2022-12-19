package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.model.Caja;
import com.salesianostriana.dam.kiloapi.model.Tiene;
import com.salesianostriana.dam.kiloapi.model.TienePK;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import com.salesianostriana.dam.kiloapi.repository.TieneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class TieneService {

    private final TieneRepository repository;
    private final CajaService cajaService;

    public Tiene add(Tiene tiene) {
        return repository.save(tiene);
    }

    public Optional<Tiene> findById(TienePK tienePK) {
        return repository.findById(tienePK);
    }

    public List<Tiene> findAll() {
        return repository.findAll();
    }

    public Tiene edit(Tiene tiene) {
        return repository.save(tiene);
    }

    public void delete(Tiene tiene) {
        repository.delete(tiene);
    }

    public void deleteById(TienePK tienePK) {
        repository.deleteById(tienePK);
    }

    public void borrarTipoDeTiene(TipoAlimento t) {
        List<Tiene> listado = this.findAll();
        List<Tiene> listAux = new ArrayList<>();

        listado.forEach(tiene -> {
            if (tiene.getTipoAlimento().equals(t)) {
                tiene.getCaja().removeTieneFromCaja(tiene);
            } else {
                listAux.add(tiene);
            }
        });

        this.cajaService.modificarTieneList(listAux);
    }

    public Optional<Tiene> findByPk(Long id1, Long id2) {

        TienePK t = new TienePK(id2, id1);

        return repository.findById(t);
    }

    public void modificarKilos(Caja caja) {
        AtomicReference<Double> newcant = new AtomicReference<>(0.0);
        caja.getTieneList().forEach(t -> {
            newcant.updateAndGet(v -> new Double((double) (v + t.getCantidadKgs())));
        });

        caja.setKilosTotales(newcant.get());

    }
}
