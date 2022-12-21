package com.salesianostriana.dam.kiloapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.Caja.CajaDto;
import com.salesianostriana.dam.kiloapi.dto.Caja.CajaDtoConverter;
import com.salesianostriana.dam.kiloapi.dto.Caja.CajaViews;
import com.salesianostriana.dam.kiloapi.model.*;
import com.salesianostriana.dam.kiloapi.repository.TieneRepository;
import com.salesianostriana.dam.kiloapi.service.CajaService;
import com.salesianostriana.dam.kiloapi.service.CajaServiceLogica;
import com.salesianostriana.dam.kiloapi.service.KilosDisponiblesService;
import com.salesianostriana.dam.kiloapi.service.TieneService;
import com.salesianostriana.dam.kiloapi.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Caja", description = "Controlador para gestionar las cajas")
public class CajaController {

    private final CajaService cajaService;
    private final KilosDisponiblesService kilosDisponiblesService;
    private final CajaServiceLogica cajaServiceLogica;
    private final TieneService tieneService;
    private final CajaDtoConverter cajaDtoConverter;
    private final TipoAlimentoService tipoAlimentoService;
    private final DestinatarioService destinatarioService;
    private final TieneRepository tieneRepository;


    @Operation(summary = "Elimina un tipo de alimento de una caja por ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "El alimento ha sido eliminado de la caja correctamente",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CajaDto.class),
                    examples = {@ExampleObject(
                            value = """
                                        {
                                            "id": 7,
                                            "qr": "http://www.caja2.com",
                                            "numCaja": 2,
                                            "kilosTotales": 9.0,
                                            "alimentos": [
                                                {
                                                    "id": 4,
                                                    "nombre": "Dodotis",
                                                    "kilosEnviados": 1.0
                                                },
                                                {
                                                    "id": 1,
                                                    "nombre": "Pasta",
                                                    "kilosEnviados": 3.0
                                                }
                                            ]
                                        }                                         
                                    """
                    )}
            )}),
            @ApiResponse(responseCode = "404",
                    description = "No encontrado",
                    content = @Content),
    })
    @JsonView(CajaViews.PostCajaTipo.class)
    @DeleteMapping("/caja/{id1}/tipo/{id2}")
    public ResponseEntity<CajaDto> deleteAlimento(@PathVariable Long id1, @PathVariable Long id2) {

        Optional<Caja> c1 = cajaService.findById(id1);
        Optional<TipoAlimento> t1 = tipoAlimentoService.findById(id2);

        Optional<Tiene> tiene = tieneService.findByPk(id2, id1);


        if (t1.isPresent() && c1.isPresent() && tiene.isPresent()) {

            tiene.get().removeCaja(c1.get());
            cajaService.add(c1.get());
            tieneService.delete(tiene.get());

            return ResponseEntity.status(HttpStatus.OK).body(cajaService.devolverCajaDto(c1.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Se añade un destinatario a una caja por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha añadido un destinatario a la caja correctamente",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CajaDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 10,
                                                    "qr": "http://www.caja3.com",
                                                    "numCaja": 3,
                                                    "kilosTotales": 4.0,
                                                    "destinatario": "Banco Alimentos Triana"
                                                }                                     
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "Datos inválidos",
                    content = @Content),
    })
    @PostMapping("/caja/{id1}/destinatario/{id2}")
    public ResponseEntity<CajaDto> aniadirDestinatario(@PathVariable Long id1, @PathVariable Long id2) {

        Optional<Caja> c1 = cajaService.findById(id1);
        Optional<Destinatario> d1 = destinatarioService.findById(id2);

        if (c1.isPresent() && d1.isPresent()) {
            c1.get().addCajaToDestinatario(d1.get());
            cajaService.add(c1.get());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(cajaService.getCajaDto(id1).get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @Operation(summary = "Obtiene un listado de cajas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado un listado de cajas",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CajaDto.class)),
                            examples = {@ExampleObject(
                                    value = """                                                                                      
                                            [
                                                {
                                                    "id": 6,
                                                    "qr": "http://www.caja1.com",
                                                    "numCaja": 1,
                                                    "kilosTotales": 8.0,
                                                    "destinatario": "Banco Alimentos Triana"
                                                },
                                                {
                                                    "id": 7,
                                                    "qr": "http://www.caja2.com",
                                                    "numCaja": 2,
                                                    "kilosTotales": 9.0,
                                                    "destinatario": "Banco Alimentos Triana"
                                                },
                                                {
                                                    "id": 8,
                                                    "qr": "http://www.caja3.com",
                                                    "numCaja": 3,
                                                    "kilosTotales": 4.0,
                                                    "destinatario": "Banco Alimentos Mairena"
                                                },
                                                {
                                                    "id": 9,
                                                    "qr": "http://www.caja4.com",
                                                    "numCaja": 4,
                                                    "kilosTotales": 1.0,
                                                    "destinatario": "Banco Alimentos Sevilla"
                                                }
                                            ]                                                                                       
                                             """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el listado de cajas",
                    content = @Content),
    })
    @GetMapping("/caja/")
    public ResponseEntity<List<CajaDto>> findAll() {

        if (cajaService.findAll().isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        List<CajaDto> list =
                cajaService.findAll()
                        .stream()
                        .map(cajaDtoConverter::cajaToGetCajaDto)
                        .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(list);
    }

    @Operation(summary = "Obtiene una caja en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la caja",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CajaDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 7,
                                                    "qr": "http://www.caja2.com",
                                                    "numCaja": 2,
                                                    "kilosTotales": 9.0,
                                                    "alimentos": [
                                                        {
                                                            "id": 4,
                                                            "nombre": "Dodotis",
                                                            "kilosEnviados": 1.0
                                                        },
                                                        {
                                                            "id": 1,
                                                            "nombre": "Pasta",
                                                            "kilosEnviados": 3.0
                                                        }
                                                    ],
                                                    "destinatarioDto": {
                                                        "id": 10,
                                                        "nombre": "Banco Alimentos Triana"
                                                    }
                                                }                                      
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la caja por el ID",
                    content = @Content),
    })
    @JsonView(CajaViews.GetCaja.class)
    @GetMapping("/caja/{id}")
    public ResponseEntity<CajaDto> obtenerUno(@PathVariable Long id) {

        Optional<Caja> caja = cajaService.findById(id);
        if(caja.isPresent()){
            Caja c = caja.get();
            return ResponseEntity.ok(cajaService.devolverCajaDestinatario(c));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Agrega una nueva caja")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha agregado la caja",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CajaDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 20,
                                                    "qr": "http://www.caja100.com",
                                                    "numCaja": 100
                                                }                                    
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha agregado la caja",
                    content = @Content),
    })
    @JsonView(CajaViews.CrearCaja.class)
    @PostMapping("/caja/")
    public ResponseEntity<CajaDto> newCaja(@RequestBody CajaDto cajaDto) {
        if (cajaDto.getNumCaja() == 0 || cajaDto.getQr() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Caja caja = cajaDtoConverter.of(cajaDto);

        caja = cajaService.add(caja);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cajaService.devolverCajaDto(caja));
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
                                                    "id": 9,
                                                    "qr": "http://www.caja4.com",
                                                    "numCaja": 4,
                                                    "kilosTotales": 8.0,
                                                    "alimentos": [
                                                        {
                                                            "id": 3,
                                                            "nombre": "Lentejas",
                                                            "kilosEnviados": 1.0
                                                        },
                                                        {
                                                            "id": 2,
                                                            "nombre": "Leche",
                                                            "kilosEnviados": 7.0
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
    @JsonView(CajaViews.PostCajaTipo.class)
    @PostMapping("/caja/{id}/tipo/{idTipoAlim}/kg/{cantidad}")
    public ResponseEntity<CajaDto> addCantidadToCaja(@PathVariable Long id,
                                                     @PathVariable Long idTipoAlim, @PathVariable Double cantidad) {
        Optional<Caja> c = cajaService.findById(id);
        if (c.isPresent()) {
            Caja caja = c.get();
            if (cajaServiceLogica.comprobarCantidad(idTipoAlim, cantidad)) {
                cajaServiceLogica.addKilostoCaja(caja, idTipoAlim, cantidad);
                tieneService.modificarKilos(caja);
                return ResponseEntity.status(HttpStatus.CREATED).body(cajaService.devolverCajaDestinatario(caja));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Operation(summary = "Modificar la cantidad de un tipo de Alimento de una determinada caja")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cantidad modificada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CajaDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 9,
                                                    "qr": "http://www.caja4.com",
                                                    "numCaja": 4,
                                                    "kilosTotales": 8.0,
                                                    "destinatario": "Banco Alimentos Sevilla",
                                                    "alimentos": [
                                                        {
                                                            "id": 3,
                                                            "nombre": "Lentejas",
                                                            "kilosEnviados": 5.0
                                                        },
                                                        {
                                                            "id": 2,
                                                            "nombre": "Leche",
                                                            "kilosEnviados": 7.0
                                                        }
                                                    ]
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content)})
    @PutMapping("/caja/{id}/tipo/{idTipoAlim}/kg/{cantidad}")
    public ResponseEntity<CajaDto> actualizarCantidadKgCaja(@PathVariable Long id,
                                                            @PathVariable Long idTipoAlim,
                                                            @PathVariable Double cantidad) {

        Optional<Caja> caja = cajaService.findById(id);

        Optional<TipoAlimento> tipoAlimento = tipoAlimentoService.findById(idTipoAlim);


        if (!caja.isPresent() || !tipoAlimento.isPresent()) {

            return ResponseEntity
                    .badRequest()
                    .build();

        } else {

            Optional<Tiene> tiene = tieneService.findByCajaAndTipoAlimento(caja.get(), tipoAlimento.get());

            tiene.get().setCantidadKgs(cantidad);
            tieneRepository.save(tiene.get());

            return ResponseEntity
                    .ok()
                    .body(cajaService.devolverCajaDto(tiene.get().getCaja()));
        }

    }

    @Operation(summary = "Modificar una caja, buscada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caja modificada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CajaDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 7,
                                                    "qr": "http://www.caja99.com",
                                                    "numCaja": 99,
                                                    "kilosTotales": 9.0,
                                                    "destinatario": "Banco Alimentos Triana",
                                                    "alimentos": [
                                                        {
                                                            "id": 4,
                                                            "nombre": "Dodotis",
                                                            "kilosEnviados": 1.0
                                                        },
                                                        {
                                                            "id": 3,
                                                            "nombre": "Lentejas",
                                                            "kilosEnviados": 5.0
                                                        },
                                                        {
                                                            "id": 1,
                                                            "nombre": "Pasta",
                                                            "kilosEnviados": 3.0
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

                        return cajaService.devolverCajaDto(cj);
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
