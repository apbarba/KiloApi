package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.model.Aportacion;
import com.salesianostriana.dam.kiloapi.model.DetalleAportacion;
import com.salesianostriana.dam.kiloapi.model.DetallesPK;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import com.salesianostriana.dam.kiloapi.repository.AportacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Optional<DetalleAportacion> devolverAportacion(Aportacion a, DetallesPK detallesPK) {
        return a.getDetalleAportacionList()
                .stream()
                .filter(det -> det.getDetallesPK().equals(detallesPK))
                .findFirst();
    }

    public void removeTipoFromDetalle(TipoAlimento t) {
        List<DetalleAportacion> listado = new ArrayList<>();
        List<DetalleAportacion> listaAux = new ArrayList<>();

        // CREACIÓN DE LISTA AUXILIAR
        this.findAll().forEach(a -> {
            a.getDetalleAportacionList().forEach(d -> {
                listado.add(d);
            });
        });

        // SETEO DE AQUELLOS DETALLES DONDE EL TIPO DE ALIMENTO SEA EL QUE SE QUIERE BORRAR
        // GUARDO AQUELLOS DETALLES DONDE EL TIPO NO SEA IGUAL QUE EL QUE VOY A BORRAR EN OTRA LISTA AUX
        listado.forEach(d -> {
            if (d.getTipoAlimento().equals(t)) {
                d.setTipoAlimento(null);
            } else {
                listaAux.add(d);
            }
        });

        // GUARDAR AQUELLOS DETALLES DONDE EL TIPO NO SEA NULL EN OTRA LISTA AUXILIAR
//        listado.forEach(d -> {
//            if (d.getTipoAlimento() != null) {
//                listaAux.add(d);
//            }
//        });

        // SETEAR LOS NUEVOS DETALLES EN SUS RESPECTIVAS APORTACIONES
        this.findAll().forEach(a -> {
            a.getDetalleAportacionList().clear();
            listaAux.forEach(l -> {
                if (l.getAportacion().equals(a) && !a.getDetalleAportacionList().equals(l)) {
                    a.addDetalleAportacion(l);
                }
            });
            this.edit(a);
        });
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

}
