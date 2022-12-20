package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.dto.Destinatario.DestinatarioDto;
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

    //Implementación de ls consulta realizada en el repositorio
    public List<DestinatarioDto> findAllDestinatario(Long id){

        return destinatarioRepository.getDestinatario(id);
    }

    public boolean comprobarDatos(Destinatario d) {
        return d.getNombre() == "" || d.getDireccion() == "" || d.getPersonaContacto() == "" || d.getTelefono() == "";
    }

    public DestinatarioDto generarDto (Destinatario d){
        DestinatarioDto dto = destinatarioRepository.crearDtoKgTotales(d.getId());
        dto.setNumCajas(destinatarioRepository.crearListaNumCajas(d.getId()));
        return dto;
    }

}
