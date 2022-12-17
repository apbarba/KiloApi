package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.model.KilosDisponibles;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import com.salesianostriana.dam.kiloapi.repository.KilosDisponiblesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
}