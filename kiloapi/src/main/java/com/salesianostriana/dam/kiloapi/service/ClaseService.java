package com.salesianostriana.dam.kiloapi.service;


import com.salesianostriana.dam.kiloapi.model.Clase;
import com.salesianostriana.dam.kiloapi.repository.ClaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ClaseService {
    private final ClaseRepository repository;


    public Clase add(Clase clase) {
        return repository.save(clase);
    }

    public Optional<Clase> findById(Long id) {
        return repository.findById(id);
    }

    public List<Clase> findAll() {
        return repository.findAll();
    }

    public Clase edit(Clase clase) {
        return repository.save(clase);
    }

    public void delete(Clase clase) {
        repository.delete(clase);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}
