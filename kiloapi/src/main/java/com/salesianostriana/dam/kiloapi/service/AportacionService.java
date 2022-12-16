package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.model.Aportacion;
import com.salesianostriana.dam.kiloapi.model.DetalleAportacion;
import com.salesianostriana.dam.kiloapi.model.DetallesPK;
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

    public Optional<DetalleAportacion> devolverAportacion(Aportacion a, DetallesPK detallesPK) {
        return a.getDetalleAportacionList()
                .stream()
                .filter(det -> det.getDetallesPK().equals(detallesPK))
                .findFirst();
    }

}
