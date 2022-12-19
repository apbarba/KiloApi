package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.dtos.GetAportacionDto;
import com.salesianostriana.dam.kiloapi.model.Aportacion;
import com.salesianostriana.dam.kiloapi.model.Clase;
import com.salesianostriana.dam.kiloapi.repository.AportacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AportacionService {

    private final AportacionRepository repository;

    public Aportacion add(Aportacion aportacion) {
        return repository.save(aportacion);
    }

    public Optional<Aportacion> findById(Long id) {
        return repository.findById(id);
    }

    public List<Aportacion> findAll() {
        return repository.findAll();
    }

    public Aportacion edit(Aportacion aportacion) {
        return repository.save(aportacion);
    }

    public void delete(Aportacion aportacion) {
        repository.delete(aportacion);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public List<Aportacion> getAportacionDto(Long id){

        return  repository.findAportacionByClase(id);
    }

}
