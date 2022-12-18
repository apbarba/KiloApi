package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.dto.AportacionDto;
import com.salesianostriana.dam.kiloapi.model.Aportacion;
import com.salesianostriana.dam.kiloapi.model.DetalleAportacion;
import com.salesianostriana.dam.kiloapi.model.DetallesPK;
import com.salesianostriana.dam.kiloapi.model.KilosDisponibles;
import com.salesianostriana.dam.kiloapi.service.AportacionService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AportacionController {

    private final AportacionService aportacionService;
    private final KilosDisponiblesService kilosDisponiblesService;

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
                kilosDisponiblesService.restarKilos(da);
                a.removeDetalleAportacion(da);
                aportacionService.edit(a);
                return ResponseEntity.ok(AportacionDto.of(a));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @Operation(summary = "Borra una aportación y sus detalles en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la aportación",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Aportacion.class))})
    })

    @DeleteMapping("/aportacion/{id}")
    public ResponseEntity<Aportacion> deleteAportacion(@PathVariable Long id){
        Optional<Aportacion> ap = aportacionService.findById(id);
        if(ap.isPresent()){
            ap.get().getDetalleAportacionList().forEach(d ->{
                kilosDisponiblesService.restarKilos(d);
            });
            aportacionService.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }
}
