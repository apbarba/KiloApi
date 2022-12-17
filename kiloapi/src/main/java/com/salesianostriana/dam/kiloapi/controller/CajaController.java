package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.dto.ConverterCaja;
import com.salesianostriana.dam.kiloapi.dto.GetCaja;
import com.salesianostriana.dam.kiloapi.repository.CajaRepository;
import com.salesianostriana.dam.kiloapi.service.CajaService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CajaController {

    private final ConverterCaja converterCaja;

    private final CajaRepository cajaRepository;

    private final CajaService cajaService;

    @GetMapping("/caja/")
    public ResponseEntity<List<GetCaja>> findAll(){

        if (cajaService.findAll().isEmpty()){

            return ResponseEntity
                    .notFound()
                    .build();
        }

        List<GetCaja> list =
                cajaService.findAll()
                        .stream()
                        .map(converterCaja::getCaja)
                        .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(list);
    }
}
