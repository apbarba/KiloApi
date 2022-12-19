package com.salesianostriana.dam.kiloapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.Caja.CajaDto;
import com.salesianostriana.dam.kiloapi.dto.Caja.CajaViews;
import com.salesianostriana.dam.kiloapi.model.Caja;
import com.salesianostriana.dam.kiloapi.model.Destinatario;
import com.salesianostriana.dam.kiloapi.service.CajaService;
import com.salesianostriana.dam.kiloapi.service.KilosDisponiblesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Caja", description = "Controlador para gestionar las cajas")
public class CajaController {

    private final CajaService cajaService;
    private final KilosDisponiblesService kilosDisponiblesService;

    @Operation(summary = "Modificar una caja, buscada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caja modificada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CajaDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 4,
                                                    "qr": "http://www.caja99.com",
                                                    "numCaja": 99,
                                                    "kilosTotales": 8.0,
                                                    "destinatario": "Banco Alimentos Triana",
                                                    "alimentos": [
                                                        {
                                                            "id": 1,
                                                            "nombre": "Pasta",
                                                            "kilosEnviado": 6.0
                                                        },
                                                        {
                                                            "id": 2,
                                                            "nombre": "Leche",
                                                            "kilosEnviados": 2.0
                                                        }
                                                    ]
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Cuerpo para la modificación aportado inválido",
                    content = @Content)})
    @JsonView(CajaViews.Master.class)
    @PutMapping("/caja/{id}")
    public ResponseEntity<CajaDto> actualizarCaja(@PathVariable Long id, @RequestBody Caja c) {
        Optional<Caja> caja = cajaService.findById(id);
        if (!caja.isPresent() || cajaService.comprobarDatos(c)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.of(
                    caja.map(cj -> {
                        cj.setQr(c.getQr());
                        cj.setNumCaja(c.getNumCaja());
                        cajaService.edit(cj);

                        return CajaDto.of(cj);
                    }));
        }
    }


    @Operation(summary = "Eliminar una caja, buscada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Caja eliminada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Caja.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {}
                                            """
                            )}
                    )})})
    @DeleteMapping("/caja/{id}")
    public ResponseEntity<?> borrarCaja(@PathVariable Long id) {
        Optional<Caja> cajaOp = cajaService.findById(id);
        if (cajaOp.isPresent()) {
            Caja caja = cajaOp.get();
            Destinatario d = caja.getDestinatario();
            if (d != null) {
                caja.removeCajaFromDestinatario(d);
            }
            kilosDisponiblesService.devolverKilos(caja);
            cajaService.delete(caja);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
