package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.model.*;
import com.salesianostriana.dam.kiloapi.repository.TieneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TieneService {

    private final TieneRepository repository;

    public Tiene add(Tiene tiene) {
        return repository.save(tiene);
    }

    public Optional<Tiene> findById(Long id1, Long id2) {

        TienePK t = TienePK.builder()
                .caja_id(id1)
                .tipoAlimento_id(id2)
                .build();

        return repository.findById(t);
    }

    public void delete(Tiene tiene) {
        repository.delete(tiene);
    }

    public void deleteById(TienePK id) {
        repository.deleteById(id);
    }
}
