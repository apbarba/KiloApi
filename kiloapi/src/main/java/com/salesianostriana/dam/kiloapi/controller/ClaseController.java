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

    @Operation(summary = "Edita las propiedades de una clase por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "La clase ha sido modificada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Clase.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna clase relacionada con ese ID",
                    content = @Content),
    })
    @PutMapping("/clase/{id}")
    public ResponseEntity<Clase> editClase(@RequestBody Clase clase, @PathVariable Long id) {
        Optional<Clase> c1 = claseService.findById(id);

        return c1.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.of(c1.map(old -> {
                    old.setAportacion(c1.get().getAportacion());
                    old.setNombre(c1.get().getNombre());
                    old.setTutor(c1.get().getTutor());
                    claseService.add(old);
                    return old;
                })
        );


    }

}
