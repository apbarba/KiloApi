package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.model.Destinatario;
import com.salesianostriana.dam.kiloapi.service.DestinatarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DestinatarioController {

    private final DestinatarioService destinatarioService;

    @Operation(summary = "Obtiene la información de un destinatario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el destinatario relacionado con ese ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Destinatario.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado un destinatario con ese ID",
                    content = @Content),
    })
    @GetMapping("/destinatario/{id}")
    public ResponseEntity<Destinatario> findById(@PathVariable Long id) {
        Optional<Destinatario> d1 = destinatarioService.findById(id);
        return d1.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                    : ResponseEntity.of(destinatarioService.findById(id));

    }

}
