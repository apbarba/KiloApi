package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.dto.TipoAlimento.TipoAlimentoDto;
import com.salesianostriana.dam.kiloapi.model.KilosDisponibles;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import com.salesianostriana.dam.kiloapi.repository.KilosDisponiblesRepository;
import com.salesianostriana.dam.kiloapi.service.KilosDisponiblesService;
import com.salesianostriana.dam.kiloapi.service.TipoAlimentoService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "KilosDisponibles", description = "Controlador para gestionar los kilos disponibles")
public class KilosDisponiblesController {
    private final KilosDisponiblesRepository kilosDisponiblesRepository;
    private final KilosDisponiblesService kgDService;
    private final TipoAlimentoService tipoAlimentoService;

    @Operation(summary = "Obtiene todos los kilos disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado todos los kilos disponibles de cada tipo de alimento",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TipoAlimentoDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                [
                                                    {
                                                        "id": 1,
                                                        "nombre": "Pasta",
                                                        "kilosDisponibles": 12.0
                                                    },
                                                    {
                                                        "id": 2,
                                                        "nombre": "Leche",
                                                        "kilosDisponibles": 25.0
                                                    },
                                                    {
                                                        "id": 3,
                                                        "nombre": "Lentejas",
                                                        "kilosDisponibles": 6.0
                                                    }
                                                ]                                 
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún kilo disponible",
                    content = @Content),
    })
    @GetMapping("/kilosDisponibles/")
    public ResponseEntity<List<TipoAlimentoDto>> getAll() {
        List<TipoAlimento> data = tipoAlimentoService.findAll();
        if (data.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(kilosDisponiblesRepository.crearTipoAlimentoDto());
        }
    }

    @Operation(summary = "Obtiene la información de los kilos disponibles por tipo de alimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado un tipo de alimento relacionado con ese ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = KilosDisponibles.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado un tipo de alimento relacionado con ese ID",
                    content = @Content),
    })
    @GetMapping("/kilosDisponibles/{idTipoAlimento}")
    public ResponseEntity<KilosDisponibles> findById(@PathVariable Long id) {

        Optional<KilosDisponibles> kilosDisponibles = kgDService.findById1(id);

        return kilosDisponibles.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.of(kgDService.findById1(id));
        //falta completar
    }
}
