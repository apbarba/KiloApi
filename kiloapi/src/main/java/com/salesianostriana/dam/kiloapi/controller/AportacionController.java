package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.dto.AportacionDto;
import com.salesianostriana.dam.kiloapi.dto.CrearAportacionDto;
import com.salesianostriana.dam.kiloapi.model.*;
import com.salesianostriana.dam.kiloapi.service.AportacionService;
import com.salesianostriana.dam.kiloapi.service.ClaseService;
import com.salesianostriana.dam.kiloapi.service.KilosDisponiblesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AportacionController {

    private final AportacionService aportacionService;
    private final KilosDisponiblesService kilosDisponiblesService;
    private final ClaseService claseService;


    @Operation(summary = "Crear nueva aportación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aportación creada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AportacionDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "fecha": "2022-12-17",
                                                    "id_clase": 11,
                                                    "detalle": {
                                                        "1": "6.0",
                                                        "3": "7.0"
                                                    }
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Cuerpo para la creación aportado inválido",
                    content = @Content)})
    @PostMapping("/aportacion")
    public ResponseEntity<AportacionDto> crearAportacion(@RequestBody CrearAportacionDto crear) {
        Optional<Clase> opC = claseService.findById(crear.getId_clase());
        if (opC.isPresent() && crear.getFecha() != null && !crear.getDetalle().isEmpty()) {
            Map<TipoAlimento, Double> aportacion = aportacionService.convertJSONToDetalles(crear.getDetalle());
            if (!aportacion.isEmpty()) {
                Aportacion a = CrearAportacionDto.generar(crear, opC.get());
                aportacionService.add(a);
                kilosDisponiblesService.agregarKilos(aportacion);
                aportacionService.addListadoDetalles(aportacion, a);
                return ResponseEntity.status(HttpStatus.CREATED).body(AportacionDto.of(a));
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
                                                    "id": 13,
                                                    "nombreCurso": "2ºDAM",
                                                    "tutor": "Luismi López",
                                                    "fecha": "2022-12-14",
                                                    "aportaciones": {
                                                        "Leche": 10.0,
                                                        "Pasta": 2.0
                                                    }
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Cuerpo para la modificación aportado inválido",
                    content = @Content)})
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
                return ResponseEntity.ok(AportacionDto.of(a));
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
                                                    "id": 12,
                                                    "nombreCurso": "1ºDAM",
                                                    "tutor": "Miguel Campos",
                                                    "fecha": "2022-12-12",
                                                    "aportaciones": {
                                                        "Lentejas": 1.0,
                                                        "Leche": 4.0
                                                    }
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404", description = "No se encuentran la línea o la aportación", content = @Content)})
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
                return ResponseEntity.ok(AportacionDto.of(a));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
