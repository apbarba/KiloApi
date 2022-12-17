package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.model.Clase;
import com.salesianostriana.dam.kiloapi.model.Destinatario;
import com.salesianostriana.dam.kiloapi.repository.DestinatarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DestinatarioService {

    private final DestinatarioRepository destinatarioRepository;

    public Destinatario add(Destinatario destinatario){

        return destinatarioRepository.save(destinatario);
    }

    public Optional<Destinatario> findById(Long id){

        return destinatarioRepository.findById(id);
    }

    public List<Destinatario> findAll(){

        return destinatarioRepository.findAll();
    }

    public Destinatario edit(Destinatario destinatario){

        return destinatarioRepository.save(destinatario);
    }

    public void delete(Destinatario destinatario){

        destinatarioRepository.delete(destinatario);
    }

    public void deleteById(Long id){

        destinatarioRepository.deleteById(id);
    }
}
