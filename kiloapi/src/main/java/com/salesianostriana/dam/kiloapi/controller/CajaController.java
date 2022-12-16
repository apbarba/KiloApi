package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.dto.CajaDto;
import com.salesianostriana.dam.kiloapi.dto.CreateCajaDto;
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
                                                    "destinatario": null,
                                                    "alimentos": [
                                                        {
                                                            "id": 1,
                                                            "nombre": "Pasta",
                                                            "kilosAportados": 6.0,
                                                            "kilosDisponibles": 12.0
                                                        },
                                                        {
                                                            "id": 2,
                                                            "nombre": "Leche",
                                                            "kilosAportados": 2.0,
                                                            "kilosDisponibles": 25.0
                                                        }
                                                    ]
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Cuerpo para la modificación aportado inválido",
                    content = @Content),
    })
    @PutMapping("/caja/{id}")
    public ResponseEntity<CajaDto> actualizarCaja(@PathVariable Long id, @RequestBody CreateCajaDto createCajaDto) {
        Optional<Caja> caja = cajaService.findById(id);
        if (caja.isPresent()) {
            Caja cajaEditada = CreateCajaDto.createCajaDtoToCaja(createCajaDto);
            return ResponseEntity.of(
                    caja.map(c -> {
                        c.setQr(cajaEditada.getQr());
                        c.setNumCaja(cajaEditada.getNumCaja());
                        cajaService.edit(c);

                        return CajaDto.mostrarDetallesCaja(c);
                    }));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
