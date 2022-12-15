package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.dto.ConverterClase;
import com.salesianostriana.dam.kiloapi.dto.CreateClase;
import com.salesianostriana.dam.kiloapi.model.Clase;
import com.salesianostriana.dam.kiloapi.repository.ClaseRepository;
import com.salesianostriana.dam.kiloapi.service.ClaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor

public class ClaseController {

   // private final ConverterClase converterClase;

   // private final CreateClase createClase;

    private final ClaseRepository claseRepository;

    private final ClaseService claseService;

    @PostMapping("/clase/")
    public ResponseEntity<Clase> newClase(@RequestBody Clase clase){

        if (clase.getNombre().isEmpty() && clase.getTutor().isEmpty()){

            return ResponseEntity
                    .badRequest()
                    .build();
        }else {

            return  ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(claseService.add(clase));
        }
    }

}
