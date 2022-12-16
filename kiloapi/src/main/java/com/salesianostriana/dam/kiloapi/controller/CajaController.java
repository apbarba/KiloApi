package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.model.Caja;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import com.salesianostriana.dam.kiloapi.service.CajaService;
import com.salesianostriana.dam.kiloapi.service.TipoAlimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CajaController {

    private final CajaService cajaService;
    private final TipoAlimentoService tipoAlimentoService;

//    @DeleteMapping("/caja/{id1}/tipoAlimento/{id2}")
//    public ResponseEntity<Caja> deleteAlimento(@PathVariable Long id1, @PathVariable Long id2) {
//
//        Optional<Caja> c1 = cajaService.findById(id1);
//        Optional<TipoAlimento> t1 = tipoAlimentoService.findById(id2);
//
//        return t1.isPresent() && c1.isPresent() ? //tipoAlimentoService.deleteById(id2)
//
//               :    ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//
//
//    }
}
