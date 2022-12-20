package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.dto.Aportacion.AportacionDto;
import com.salesianostriana.dam.kiloapi.model.Aportacion;
import com.salesianostriana.dam.kiloapi.model.DetalleAportacion;
import com.salesianostriana.dam.kiloapi.model.DetallesPK;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import com.salesianostriana.dam.kiloapi.repository.AportacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class AportacionService {


    private final AportacionRepository repository;
    private final TipoAlimentoService tipoAlimentoService;

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

    public Optional<DetalleAportacion> devolverAportacion(Aportacion a, DetallesPK detallesPK) {
        return a.getDetalleAportacionList()
                .stream()
                .filter(det -> det.getDetallesPK().equals(detallesPK))
                .findFirst();
    }

    public Map<TipoAlimento, Double> convertJSONToDetalles(Map<Long, Double> json) {
        Map<TipoAlimento, Double> aportacion = new HashMap<>();
        json.keySet().forEach(idTipo -> {
            Optional<TipoAlimento> t = tipoAlimentoService.findById(idTipo);
            if (t.isPresent()) {
                aportacion.put(t.get(), json.get(t.get().getId()));
            }
        });
        return aportacion;
    }

    public void addListadoDetalles(Map<TipoAlimento, Double> aportacion, Aportacion a) {
        List<DetalleAportacion> listAux = new ArrayList<>();
        aportacion.keySet().forEach(tipo -> {
            listAux.add(DetalleAportacion
                    .builder()
                    .detallesPK(new DetallesPK(a.getId(), listAux.size() + 1))
                    .cantidadKg(aportacion.get(tipo))
                    .tipoAlimento(tipo)
                    .build());
        });

        listAux.forEach(a::addDetalleAportacion);
        this.edit(a);
    }

    public AportacionDto devolverAportacionDto(Aportacion a) {
        AportacionDto adto = repository.generarAportacionDto(a.getId());
        adto.setAportaciones(repository.generarListadoAportaciones(a.getId()));

        return adto;
    }

    public boolean comprobarExistenciaEnAportacion(TipoAlimento t) {
        AtomicBoolean existe = new AtomicBoolean(false);
        this.findAll().forEach(a -> {
            a.getDetalleAportacionList().forEach(d -> {
                if (d.getTipoAlimento().equals(t)) {
                    // Si existe, no se puede borrar
                    existe.set(true);
                }
            });
        });
        return existe.get();
    }

}
