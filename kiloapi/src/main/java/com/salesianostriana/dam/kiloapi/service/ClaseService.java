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

    private final ClaseRepository claseRepository;

    public Clase add(Clase clase){

        return claseRepository.save(clase);
    }

    public Optional<Clase> findById(Long id){

        return claseRepository.findById(id);
    }

    public List<Clase> findAll(){

        return claseRepository.findAll();
    }

    public Clase edit(Clase clase){

        return claseRepository.save(clase);
    }

    public void delete(Clase clase){

        claseRepository.delete(clase);
    }

    public void deleteById(Long id){

        claseRepository.deleteById(id);
    }
}
