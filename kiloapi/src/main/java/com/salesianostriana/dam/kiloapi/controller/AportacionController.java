package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.dto.ConverterAportacion;
import com.salesianostriana.dam.kiloapi.dto.GetAportacion;
import com.salesianostriana.dam.kiloapi.model.Aportacion;
import com.salesianostriana.dam.kiloapi.model.Clase;
import com.salesianostriana.dam.kiloapi.model.DetalleAportacion;
import com.salesianostriana.dam.kiloapi.repository.AportacionRepository;
import com.salesianostriana.dam.kiloapi.service.AportacionesService;
import com.salesianostriana.dam.kiloapi.service.ClaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AportacionController {

    private final AportacionRepository aportacionRepository;

    private final ConverterAportacion converterAportacion;

    private final AportacionesService aportacionesService;

    private final ClaseService claseService;

    @GetMapping("/aportacion/{id}")
    public ResponseEntity<List<GetAportacion>> findById(@PathVariable Long id) {

        if (aportacionesService.findById(id).isEmpty()){

            return ResponseEntity
                    .ok()
                    .build();
        }
        List<GetAportacion> getAportacionList =
                    aportacionesService.findById(id).stream()
                        .map(converterAportacion::getAportacionDetallesDto)
                        .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(getAportacionList);
    }

    @GetMapping("/aportacion/{idClase}")
    public ResponseEntity<List<GetAportacion>> getAportacionesByIdClase(@PathVariable Long idClase){

        Optional<Clase> clase = claseService.findById(idClase);

        if (clase.isEmpty()){

            return ResponseEntity
                    .notFound()
                    .build();
        }

        List<GetAportacion> aportaciones = clase.get().getAportacionList().stream().
                map(aportacion -> {
                    List<Pair<String, Integer>> alimentos = 
                            
                            aportacion.getFecha().stream()
                })


    }
}
