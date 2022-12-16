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

    @Operation(summary = "Elimina un destinatario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "El destinatario ha sido eliminado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Destinatario.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra un destinatario con este ID",
                    content = @Content),
    })
    @DeleteMapping("/destinatario/{id}")
    public ResponseEntity<Destinatario> delete(@PathVariable Long id) {
        Optional<Destinatario> d1 = destinatarioService.findById(id);
        return d1.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
