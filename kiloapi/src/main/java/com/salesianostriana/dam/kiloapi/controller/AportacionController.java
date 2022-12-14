package com.salesianostriana.dam.kiloapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.Aportacion.AportacionDto;
import com.salesianostriana.dam.kiloapi.dto.Aportacion.AportacionDtoConverter;
import com.salesianostriana.dam.kiloapi.dto.Aportacion.AportacionViews;
import com.salesianostriana.dam.kiloapi.dto.Aportacion.CrearAportacionDto;
import com.salesianostriana.dam.kiloapi.model.*;
import com.salesianostriana.dam.kiloapi.service.AportacionService;
import com.salesianostriana.dam.kiloapi.service.ClaseService;
import com.salesianostriana.dam.kiloapi.service.KilosDisponiblesService;
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

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequiredArgsConstructor
@Tag(name = "Aportación", description = "Controlador para gestionar las aportaciones")
public class AportacionController {

    private final AportacionService aportacionService;
    private final KilosDisponiblesService kilosDisponiblesService;
    private final ClaseService claseService;
    private final AportacionDtoConverter aportacionDtoConverter;

    @Operation(summary = "Obtiene todas las aportaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado aportaciones realizadas",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AportacionDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                [
                                                    {
                                                        "nombreClase": "1ºDAM",
                                                        "fecha": "2022-12-12",
                                                        "cantidadkgTotales": 7.0
                                                    },
                                                    {
                                                        "nombreClase": "1ºDAM",
                                                        "fecha": "2022-12-15"
                                                    },
                                                    {
                                                        "nombreClase": "2ºDAM",
                                                        "fecha": "2022-12-14",
                                                        "cantidadkgTotales": 6.0
                                                    },
                                                    {
                                                        "nombreClase": "2ºDAM",
                                                        "fecha": "2022-12-16",
                                                        "cantidadkgTotales": 0.0
                                                    }
                                                ]                                      
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna aportación",
                    content = @Content),
    })
    @GetMapping("/aportacion/")
    @JsonView({AportacionViews.FindAllAportaciones.class})
    public ResponseEntity<List<AportacionDto>> findAll() {
        List<AportacionDto> aportacionList = aportacionService.findAllAportaciones();
        return aportacionList.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).
                body(aportacionList);

    }

    @Operation(summary = "Obtener una lista con todas las aportaciones de una clase correspondiente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Clase Encontrada",
                    content = {@Content(mediaType = "aplication/json",
                            schema = @Schema(implementation = AportacionDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                [
                                                    {
                                                        "fecha": "2022-12-14",
                                                        "listaAlimentos": {
                                                            "Leche": 4.0,
                                                            "Pasta": 2.0
                                                        }
                                                    },
                                                    {
                                                        "fecha": "2022-12-16",
                                                        "listaAlimentos": {
                                                            "Dodotis": 0.0
                                                        }
                                                    }
                                                ]                                                                                
                                            """
                            )}
                    )}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Clase inexistente",
                    content = @Content)})
    @GetMapping("/aportacion/clase/{id}")
    public ResponseEntity<List<AportacionDto>> findAll(@PathVariable Long id) {

        List<Aportacion> lista = aportacionService.getAportacionDto(id);

        if (lista.isEmpty()) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();

        } else {
            List<AportacionDto> listaAport = new ArrayList<>();

            for (Aportacion a : lista) {
                Map<String, Double> mapa = new HashMap<>();

                for (DetalleAportacion d : a.getDetalleAportacionList()) {
                    mapa.put(d.getTipoAlimento().getNombre(), d.getCantidadKg());
                }
                listaAport.add(aportacionDtoConverter.aportacionToGetAportacionDto(a, mapa));
            }

            return ResponseEntity.status(HttpStatus.OK).
                    body(listaAport);
        }
    }
    @Operation(summary = "Se ha encontrado la aportacion y muestra todos sus detalles")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Clase Encontrada",
                    content = {@Content(mediaType = "aplication/json",
                            schema = @Schema(implementation = AportacionDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                        "id": 16,
                                                        "fecha": "2022-12-14",
                                                        "aportaciones": [
                                                            {
                                                                "numLinea": 1,
                                                                "tipoAlimento": "Pasta",
                                                                "cantidadAportada": 2.0
                                                            },
                                                            {
                                                                "numLinea": 2,
                                                                "tipoAlimento": "Leche",
                                                                "cantidadAportada": 4.0
                                                            }
                                                        ]
                                                }
                                    """
                            )}

                    )}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aportacion inexistente",
                    content = @Content)})
     @GetMapping("/aportacion/{id}")
     public ResponseEntity<List<AportacionDto>> findById(@PathVariable Long id) {

        Optional<Aportacion> a1 = aportacionService.findById(id);

        List<Aportacion> aportacionList = aportacionService.getAportacion(id);

       if (a1.isEmpty()) {

         return ResponseEntity
                 .notFound()
                .build();
    }

    return ResponseEntity
          .ok()
        .body(aportacionList.stream()
                .map(aportacionDtoConverter::aportacionToGetAportacionDto2).toList()
        );
    }


    @Operation(summary = "Crear nueva aportación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aportación creada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AportacionDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 19,
                                                    "nombreCurso": "2ºDAM",
                                                    "tutor": "Luismi López",
                                                    "fecha": "2022-12-17",
                                                    "aportaciones": [
                                                        {
                                                            "numLinea": 1,
                                                            "tipoAlimento": "Pasta",
                                                            "cantidadAportada": 6.0
                                                        },
                                                        {
                                                            "numLinea": 2,
                                                            "tipoAlimento": "Lentejas",
                                                            "cantidadAportada": 7.0
                                                        }
                                                    ]
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Cuerpo para la creación aportado inválido",
                    content = @Content)})
    @JsonView(AportacionViews.Master.class)
    @PostMapping("/aportacion/")
    public ResponseEntity<AportacionDto> crearAportacion(@RequestBody CrearAportacionDto crear) {
        Optional<Clase> opC = claseService.findById(crear.getId_clase());
        if (opC.isPresent() && crear.getFecha() != null && !crear.getDetalle().isEmpty()) {
            Map<TipoAlimento, Double> aportacion = aportacionService.convertJSONToDetalles(crear.getDetalle());
            if (!aportacion.isEmpty()) {
                Aportacion a = Aportacion.builder()
                        .fecha(crear.getFecha())
                        .clase(opC.get())
                        .build();
                aportacionService.add(a);
                kilosDisponiblesService.agregarKilos(aportacion);
                aportacionService.addListadoDetalles(aportacion, a);
                return ResponseEntity.status(HttpStatus.CREATED).body(aportacionService.devolverAportacionDto(a));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @Operation(summary = "Modificar los kg de una línea de aportación, buscado por su ID {id} y línea {num}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kg modificados correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AportacionDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 16,
                                                    "nombreCurso": "2ºDAM",
                                                    "tutor": "Luismi López",
                                                    "fecha": "2022-12-14",
                                                    "aportaciones": [
                                                        {
                                                            "numLinea": 1,
                                                            "tipoAlimento": "Pasta",
                                                            "cantidadAportada": 2.0
                                                        },
                                                        {
                                                            "numLinea": 2,
                                                            "tipoAlimento": "Leche",
                                                            "cantidadAportada": 10.0
                                                        }
                                                    ]
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Cuerpo para la modificación aportado inválido",
                    content = @Content)})
    @JsonView(AportacionViews.Master.class)
    @PutMapping("/aportacion/{id}/linea/{num}/kg/{numKg}")
    public ResponseEntity<AportacionDto> modificarAportacion(@PathVariable Long id, @PathVariable Long num, @PathVariable double numKg) {
        DetallesPK detallesPK = new DetallesPK(id, num);
        Optional<Aportacion> aportacion = aportacionService.findById(id);

        if (aportacion.isPresent()) {
            Aportacion a = aportacion.get();
            Optional<DetalleAportacion> opDa = aportacionService.devolverAportacion(a, detallesPK);
            if (opDa.isPresent()) {
                DetalleAportacion da = opDa.get();
                double cantidadPrevia = da.getCantidadKg();
                da.setCantidadKg(numKg);
                kilosDisponiblesService.modificarKilos(da, 2, cantidadPrevia);
                aportacionService.edit(a);
                return ResponseEntity.ok(aportacionService.devolverAportacionDto(a));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @Operation(summary = "Eliminar una línea, buscada por su ID (num), de una aportación, buscada por su ID (id)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Línea eliminada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AportacionDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 15,
                                                    "nombreClase": "1ºDAM",
                                                    "tutor": "Miguel Campos",
                                                    "fecha": "2022-12-12",
                                                    "aportaciones": [
                                                        {
                                                            "numLinea": 1,
                                                            "tipoAlimento": "Pasta",
                                                            "cantidadAportada": 2.0
                                                        },
                                                        {
                                                            "numLinea": 2,
                                                            "tipoAlimento": "Leche",
                                                            "cantidadAportada": 4.0
                                                        }
                                                    ]
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "No se encuentran la línea o la aportación", content = @Content)})
    @JsonView(AportacionViews.Master.class)
    @DeleteMapping("/aportacion/{id}/linea/{num}")
    public ResponseEntity<AportacionDto> borrarDetalle(@PathVariable Long id, @PathVariable Long num) {
        DetallesPK detallesPK = new DetallesPK(id, num);
        Optional<Aportacion> aportacion = aportacionService.findById(id);

        if (aportacion.isPresent()) {
            Aportacion a = aportacion.get();
            Optional<DetalleAportacion> opDa = aportacionService.devolverAportacion(a, detallesPK);
            if (opDa.isPresent()) {
                DetalleAportacion da = opDa.get();
                kilosDisponiblesService.modificarKilos(da, 1, 0);
                a.removeDetalleAportacion(da);
                aportacionService.edit(a);
                return ResponseEntity.ok(aportacionService.devolverAportacionDto(a));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(summary = "Borra una aportación y sus detalles en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la aportación",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Aportacion.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {}
                                            """
                            )}
                    )})
    })
    @DeleteMapping("/aportacion/{id}")
    public ResponseEntity<Aportacion> deleteAportacion(@PathVariable Long id) {
        AtomicBoolean borrado = new AtomicBoolean(false);
        Optional<Aportacion> ap = aportacionService.findById(id);
        if (ap.isPresent()) {
            ap.get().getDetalleAportacionList().forEach(d -> {
                if(aportacionService.comprobarKilos(d)){
                    kilosDisponiblesService.restarKilos(d);
                    borrado.set(true);
                }
            });
            if(borrado.get()){
                aportacionService.deleteById(id);
            }
        }
        return ResponseEntity.noContent().build();
    }

}
