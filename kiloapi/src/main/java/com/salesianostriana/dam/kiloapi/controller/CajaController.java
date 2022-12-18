package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.dtos.CajaDtoConverter;
import com.salesianostriana.dam.kiloapi.dtos.GetCajaDto;
import com.salesianostriana.dam.kiloapi.model.*;
import com.salesianostriana.dam.kiloapi.service.CajaService;
import com.salesianostriana.dam.kiloapi.service.DestinatarioService;
import com.salesianostriana.dam.kiloapi.service.TieneService;
import com.salesianostriana.dam.kiloapi.service.TipoAlimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CajaController {

    private final CajaService cajaService;
    private final TipoAlimentoService tipoAlimentoService;

    private final DestinatarioService destinatarioService;

    private final TieneService tieneService;

    private final CajaDtoConverter cajaDtoConverter;


    @Operation(summary = "Elimina un tipo de alimento de una caja por ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description
            = "El alimento ha sido eliminado de la caja correctamente", content
            = {@Content(mediaType = "application/json", schema = @Schema(implementation = TipoAlimento.class))}),
            @ApiResponse(responseCode = "404", description = "No se encuentra un alimento relacionado con este ID", content = @Content),})
    @DeleteMapping("/caja/{id1}/tipoAlimento/{id2}")
    public ResponseEntity<Caja> deleteAlimento(@PathVariable Long id1, @PathVariable Long id2) {

        Optional<Caja> c1 = cajaService.findById(id1);
        Optional<TipoAlimento> t1 = tipoAlimentoService.findById(id2);

        Optional<Tiene> tiene = tieneService.findById(id1, id2);


        if (t1.isPresent() && c1.isPresent() && tiene.isPresent()) {

            tiene.get().removeCajaYAlimento(c1.get(), t1.get());
            cajaService.add(c1.get());
            tipoAlimentoService.add(t1.get());
            tieneService.delete(tiene.get());

            return ResponseEntity.status(HttpStatus.OK).body(c1.get());
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("/caja/{id1}/destinatario/{id2}")
    public ResponseEntity<GetCajaDto> aniadirDestinatario(@PathVariable Long id1, @PathVariable Long id2) {

        Optional<Caja> c1 = cajaService.findById(id1);
        Optional<Destinatario> d1 = destinatarioService.findById(id2);

        if (c1.isPresent() && d1.isPresent()) {
            c1.get().addDestinatario(d1.get());
            cajaService.add(c1.get());
            return ResponseEntity.status(HttpStatus.CREATED).body(cajaDtoConverter.cajaToGetCajaDto(c1.get()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
}
