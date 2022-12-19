package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.model.Clase;
import com.salesianostriana.dam.kiloapi.model.KilosDisponibles;
import com.salesianostriana.dam.kiloapi.service.KilosDisponiblesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NoArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Component
@NoArgsConstructor
public class KilosDisponiblesController {

    private KilosDisponiblesService kgDService;

    @Operation(summary = "Obtiene la información de los kilos disponibles por tipo de alimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado un tipo de alimento relacionado con ese ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = KilosDisponibles.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado un tipo de alimento relacionado con ese ID",
                    content = @Content),
    })
    @GetMapping("/kilosDisponibles/{idTipoAlimento}")
    public ResponseEntity<KilosDisponibles> findById(@PathVariable Long id) {

        Optional<KilosDisponibles> kilosDisponibles = kgDService.findById(id);

        return kilosDisponibles.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.of(kgDService.findById(id));
        //falta completar
    }
}
