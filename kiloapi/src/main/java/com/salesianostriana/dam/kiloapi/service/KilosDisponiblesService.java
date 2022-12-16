package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.model.KilosDisponibles;
import com.salesianostriana.dam.kiloapi.repository.KilosDisponiblesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KilosDisponiblesService {

    private final KilosDisponiblesRepository repository;


    public KilosDisponibles add(KilosDisponibles kilosDisponibles) {
        return repository.save(kilosDisponibles);
    }

    public Optional<KilosDisponibles> findById(Long id) {
        return repository.findById(id);
    }

    public List<KilosDisponibles> findAll() {
        return repository.findAll();
    }

    public KilosDisponibles edit(KilosDisponibles kilosDisponibles) {
        return repository.save(kilosDisponibles);
    }

    public void delete(KilosDisponibles kilosDisponibles) {
        repository.delete(kilosDisponibles);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
