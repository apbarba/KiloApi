package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.model.Clase;
import com.salesianostriana.dam.kiloapi.service.ClaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ClaseController {

    private final ClaseService claseService;


    @Operation(summary = "Obtiene todas las clases")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado clases",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Clase.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "nombre": "1ºDAM","tutor": "Miguel Campos"},
                                                {"id": 2, "nombre": "2ºDAM","tutor": "Luis Miguel López Magaña"}
                                            ]                                          
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna clase",
                    content = @Content),
    })
    @GetMapping("/clase/")
    public ResponseEntity<List<Clase>> findAll() {
        List<Clase> claseList = claseService.findAll();
        return claseList.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).body(claseList);


    }
}
