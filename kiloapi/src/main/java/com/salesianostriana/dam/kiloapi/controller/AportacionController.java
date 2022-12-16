package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.dto.ConverterAportacion;
import com.salesianostriana.dam.kiloapi.dto.GetAportacion;
import com.salesianostriana.dam.kiloapi.model.Aportacion;
import com.salesianostriana.dam.kiloapi.repository.AportacionRepository;
import com.salesianostriana.dam.kiloapi.service.AportacionesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AportacionController {

    private final AportacionRepository aportacionRepository;

    private final ConverterAportacion converterAportacion;

    private final AportacionesService aportacionesService;

    @GetMapping("/aportacion/{id}")
    public ResponseEntity<List<GetAportacion>> findById(@RequestBody Aportacion aportacion,
                                                        @PathVariable Long id) {
        if (aportacionesService.findById(id).isEmpty()){

            return ResponseEntity
                    .ok()
                    .build();
        }
       // List<GetAportacion> getAportacionList =
                //    aportacionesService.findById(id).stream()
                       // .map(converterAportacion::getAportacionDetallesDto)
                      //  .collect(Collectors.toList());
    }
}
