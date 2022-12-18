package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.model.Caja;
import com.salesianostriana.dam.kiloapi.model.Destinatario;
import com.salesianostriana.dam.kiloapi.repository.CajaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CajaService {

    private final CajaRepository repository;

    public Caja add(Caja caja) {
        return repository.save(caja);
    }

    public Optional<Caja> findById(Long id) {
        return repository.findById(id);
    }

    public List<Caja> findAll() {
        return repository.findAll();
    }

    public Caja edit(Caja caja) {
        return repository.save(caja);
    }

    public void delete(Caja caja) {
        repository.delete(caja);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    //Método para recuperar las cajas asignadas a un destinatario(id) a partir de una consulta
    //Endpoint de get /destinatario/{id}/detalles

   // public List<Caja> findByDestinatario(Destinatario destinatario){

     //   return repository.findByDestinatario(destinatario);
   // }

}
