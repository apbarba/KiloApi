package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.model.Tiene;
import com.salesianostriana.dam.kiloapi.model.TienePK;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import com.salesianostriana.dam.kiloapi.repository.TieneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

//        listado.forEach(tiene -> {
//            if (tiene.getCaja() != null) {
//                listAux.add(tiene);
//            }
//        });

        this.cajaService.modificarTieneList(listAux);
    }

}
