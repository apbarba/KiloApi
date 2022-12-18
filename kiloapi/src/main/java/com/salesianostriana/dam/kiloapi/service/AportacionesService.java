package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.model.Aportacion;
import com.salesianostriana.dam.kiloapi.model.Clase;
import com.salesianostriana.dam.kiloapi.repository.AportacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AportacionesService {

    private final AportacionRepository aportacionRepository;

    public Aportacion add(Aportacion aportacion){

        return aportacionRepository.save(aportacion);
    }

    public Optional<Aportacion> findById(Long id){

        return aportacionRepository.findById(id);
    }

    public List<Aportacion> findAll(){

        return aportacionRepository.findAll();
    }

    public Aportacion edit(Aportacion aportacion){

        return aportacionRepository.save(aportacion);
    }

    public void delete(Aportacion aportacion){

        aportacionRepository.delete(aportacion);
    }

    public void deleteById(Long id){

        aportacionRepository.deleteById(id);
    }

    public List<Aportacion> getAportacionDto(Long id){

        return aportacionRepository.findAportacionByClase(id);
    }
}
