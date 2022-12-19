package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.dtos.AportacionDtoConverter;
import com.salesianostriana.dam.kiloapi.dtos.GetAportacionDto;
import com.salesianostriana.dam.kiloapi.model.Aportacion;
import com.salesianostriana.dam.kiloapi.model.Clase;
import com.salesianostriana.dam.kiloapi.model.DetalleAportacion;
import com.salesianostriana.dam.kiloapi.service.AportacionService;
import com.salesianostriana.dam.kiloapi.service.ClaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;


@Controller
@RequiredArgsConstructor
public class AportacionController {

    private final AportacionService aportacionService;

    private final ClaseService claseService;

    private final AportacionDtoConverter aportacionDtoConverter;

    @Operation(summary = "Obtiene todas las aportaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado aportaciones realizadas",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Clase.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"fecha": 02/12/2014, "nombre": "1ºDAM","numKgsTotales": 22},
                                                {"fecha": 04/06/2022, "nombre": "2ºDAM","numKgsTotales": 23}
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna aportación",
                    content = @Content),
    })
    @GetMapping("/aportacion/")
    public ResponseEntity<List<Aportacion>> findAll() {
        List<Aportacion> aportacionList = aportacionService.findAll();
        return aportacionList.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).body(aportacionList);

    }


    @GetMapping("/aportacion/clase/{id}")
    public ResponseEntity<List<GetAportacionDto>> findAll(@PathVariable Long id) {

        List<Aportacion> lista = aportacionService.getAportacionDto(id);

        if (lista.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } else {
            List<GetAportacionDto> listaAport = new ArrayList<>();
            for (Aportacion a:lista) {
                Map<String, Double> mapa = new HashMap<>();
                for (DetalleAportacion d: a.getDetalleAportacionList()) {
                    mapa.put(d.getTipoAlimento().getNombre(), d.getCantidadKg());
                }
                listaAport.add(aportacionDtoConverter.aportacionToGetAportacionDto(a, mapa));
            }

            return ResponseEntity.status(HttpStatus.OK).
                    body(listaAport);
        }
    }


}
