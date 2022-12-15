package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.model.Clase;
import com.salesianostriana.dam.kiloapi.model.KilosDisponibles;
import lombok.NoArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Component
@NoArgsConstructor
public class KilosDisponiblesController {

    //private KilosDisponiblesService kgDService;


   /* @GetMapping("/kilosDisponibles/{idTipoALimento}")
    public ResponseEntity<KilosDisponibles>findAll(@PathVariable Long id){


            Optional<KilosDisponibles> kilosDisponibles = kgDService.findAll();
            return kilosDisponibles.isEmpty() ?
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                    : ResponseEntity.status(HttpStatus.OK).body(kilosDisponibles);


        }


    }*/
}
