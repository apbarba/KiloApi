package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.dto.ConverterClase;
import com.salesianostriana.dam.kiloapi.dto.CreateClase;
import com.salesianostriana.dam.kiloapi.dto.GetClase;
import com.salesianostriana.dam.kiloapi.model.Clase;
import com.salesianostriana.dam.kiloapi.repository.ClaseRepository;
import com.salesianostriana.dam.kiloapi.service.ClaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import net.bytebuddy.implementation.Implementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor

public class ClaseController {

   private final ConverterClase converterClase;

   // private final CreateClase createClase;

    private final ClaseRepository claseRepository;

    private final ClaseService claseService;


    @Operation(summary = "Crear una nueva clase")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Clase creado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Clase.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {"id": 1, "nombre": "2DAM", "Tutor": ""Luismi}
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "DATOS ERRÓNEOS",
                    content = @Content)})
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

    @GetMapping("/clase/{id}")
    public ResponseEntity<List<GetClase>> findById(@RequestBody Clase clase,
                                          @PathVariable Long id){

    if (claseService.findById(id).isEmpty()){

        return ResponseEntity
                .notFound()
                .build();
    }
    List<GetClase> getClases =
            claseService.findById(id).stream()
                    .map(converterClase::claseToGetClaseDto)
                    .collect(Collectors.toList());

    return ResponseEntity
            .ok()
            .body(getClases);
    }

}
