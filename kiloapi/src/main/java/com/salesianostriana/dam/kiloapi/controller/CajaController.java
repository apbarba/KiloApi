package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.dto.CajaDto;
import com.salesianostriana.dam.kiloapi.model.*;
import com.salesianostriana.dam.kiloapi.service.CajaService;
import com.salesianostriana.dam.kiloapi.service.KilosDisponiblesService;
import com.salesianostriana.dam.kiloapi.service.TieneService;
import com.salesianostriana.dam.kiloapi.service.TipoAlimentoService;
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

    private final TipoAlimentoService tipoAlimentoService;

    private final TieneService tieneService;

    @Operation(summary = "Obtiene una caja en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la caja",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CajaDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                                                        
                                                {
                                                    "id": 4,
                                                    "qr": "http://www.caja1.com",
                                                    "numCaja": 1,
                                                    "kilosTotales": 8.0,
                                                    "alimentos": [
                                                        {
                                                            "id": 1,
                                                            "nombre": "Pasta",
                                                            "kilosDisponibles": 12.0
                                                        },
                                                        {
                                                            "id": 2,
                                                            "nombre": "Leche",
                                                            "kilosDisponibles": 25.0
                                                        }
                                                    ],
                                                    "destinatarioDto": {
                                                        "id": 12,
                                                        "nombre": "Banco Alimentos Triana",
                                                    }
                                                }
                                                                                      
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la caja por el ID",
                    content = @Content),
    })
    @GetMapping("/caja/{id}")
    public ResponseEntity<CajaDto> obtenerUno(@PathVariable Long id) {

        return ResponseEntity.of(cajaService.findById(id)
                .map(CajaDto::of));

    }

    @Operation(summary = "Agrega una nueva caja")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha agregado la caja",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Caja.class),
                            examples = {@ExampleObject(
                                    value = """
                                                                                        
                                                {
                                                    "id": 12,
                                                    "qr": "http://www.caja1.com",
                                                    "numCaja": 1,
                                                    "kilosTotales": 0.0,
                                                    "destinatario": null
                                                }
                                                                                      
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha agregado la caja",
                    content = @Content),
    })
    //FALTA JSON VIEW
    @PostMapping("/caja/")
    public ResponseEntity<Caja> newCaja(@RequestBody CajaDto cajaDto) {
        if (cajaDto.getNumCaja() == 0 || cajaDto.getQr() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Caja caja = Caja.of(cajaDto);

        caja = cajaService.add(caja);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(caja);
    }

    @Operation(summary = "Agrega a la caja la cantidad de kilos del tipo de alimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha agregado la caja",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CajaDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 4,
                                                    "qr": "http://www.caja99.com",
                                                    "numCaja": 99,
                                                    "kilosTotales": 8.0,
                                                    "alimentos": [
                                                        {
                                                            "id": 1,
                                                            "nombre": "Pasta",
                                                            "kilosDisponibles": 12.0
                                                        },
                                                        {
                                                            "id": 2,
                                                            "nombre": "Leche",
                                                            "kilosDisponibles": 25.0
                                                        }
                                                    ]
                                                }
                                            """

                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha agregado a la caja la cantidad de kilos de tipo alimento",
                    content = @Content)
    })
    @PostMapping("/caja/{id}/tipo/{idTipoAlim}/kg/{cantidad}")
    public ResponseEntity<CajaDto> addCantidadToCaja(@PathVariable Long id,
                                                     @PathVariable Long idTipoAlim, @PathVariable Double cantidad) {
        Optional<Caja> c = cajaService.findById(id);
        if (c.isPresent()) {
            Caja caja = c.get();
            if (cajaService.comprobarCantidad(id, idTipoAlim, cantidad)) {
                cajaService.addKilostoCaja(caja, id, idTipoAlim, cantidad);
                return ResponseEntity.status(HttpStatus.CREATED).body(CajaDto.of(caja));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

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
                                                    "destinatario": {
                                                        "id": 7,
                                                        "nombre": "Banco Alimentos Triana",
                                                        "direccion": "C/ Castilla, 10",
                                                        "personaContacto": "Antonio Álvarez",
                                                        "telefono": "123456789"
                                                    },
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
                    content = @Content)})
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

                        return CajaDto.mostrarDetallesCaja(cj);
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
