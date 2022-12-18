package com.salesianostriana.dam.kiloapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.TipoAlimento.TipoAlimentoDto;
import com.salesianostriana.dam.kiloapi.dto.TipoAlimento.TipoAlimentoViews;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import com.salesianostriana.dam.kiloapi.repository.TipoAlimentoRepository;
import com.salesianostriana.dam.kiloapi.service.AportacionService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "TipoAlimento", description = "Controlador para gestionar los tipos de alimentos")
public class TipoAlimentoController {

    private final TipoAlimentoService tipoAlimentoService;
    private final TipoAlimentoRepository repository;
    private final AportacionService aportacionService;
    private final TieneService tieneService;

    @Operation(summary = "Obtener detalles de un tipo de alimento por su ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipo de alimento encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TipoAlimentoDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {"id": 2, "nombre": "Leche", "kilosDisponibles": 25.0}
                                            """
                            )}
                    )}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tipo de alimento no encontrado",
                    content = @Content)})
    @GetMapping("/tipoAlimento/{id}")
    @JsonView(TipoAlimentoViews.MostrarDisponible.class)
    public ResponseEntity<TipoAlimentoDto> mostrarDetallesTipo(@PathVariable Long id) {
        Optional<TipoAlimento> tipoAlimento = tipoAlimentoService.findById(id);
        if (tipoAlimento.isPresent()) {
            return ResponseEntity.ok(repository.detallesAlimento(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @Operation(summary = "Eliminar un tipo de alimento, buscado por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de alimento eliminado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TipoAlimento.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {}
                                            """
                            )})})})
    @DeleteMapping("/tipoAlimento/{id}")
    public ResponseEntity<?> borrarTipoAlimento(@PathVariable Long id) {
        Optional<TipoAlimento> tipoAlimento = tipoAlimentoService.findById(id);
        if (tipoAlimento.isPresent()) {
            TipoAlimento ta = tipoAlimento.get();
            aportacionService.removeTipoFromDetalle(ta);
            tieneService.borrarTipoDeTiene(ta);
            tipoAlimentoService.delete(ta);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
