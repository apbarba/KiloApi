package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.model.Caja;
import com.salesianostriana.dam.kiloapi.model.Tiene;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CajaService {

    public boolean comprobarDatos(Caja caja) {
        return caja.getQr() == "" || caja.getNumCaja() <= 0;
    }

    public void calcularKg(Caja c) {
        c.setKilosTotales(0);
        c.getTieneList().forEach(t -> {
            c.setKilosTotales(c.getKilosTotales() + t.getCantidadKgs());
        });
        this.edit(c);
    }

    public void modificarTieneList(List<Tiene> list) {
        this.findAll().forEach(c -> {
            c.getTieneList().clear();
            list.forEach(l -> {
                if (l.getCaja().equals(c) && !c.getTieneList().equals(l)) {
                    c.addTieneToCaja(l);
                }
            });
            this.calcularKg(c);
        });
    }

    public Optional<GetCajaDto> getCajaDto(Long id) {
        return repository.getCajaDto(id);

    }

    //Método para recuperar las cajas asignadas a un destinatario(id) a partir de una consulta
    //Endpoint de get /destinatario/{id}/detalles

    // public List<Caja> findByDestinatario(Destinatario destinatario){

    //   return repository.findByDestinatario(destinatario);
    // }

}
