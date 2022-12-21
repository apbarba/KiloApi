package com.salesianostriana.dam.kiloapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.Aportacion.AportacionDto;
import com.salesianostriana.dam.kiloapi.dto.Aportacion.AportacionDtoConverter;
import com.salesianostriana.dam.kiloapi.dto.TipoAlimento.TipoAlimentoDto;
import com.salesianostriana.dam.kiloapi.dto.TipoAlimento.TipoAlimentoViews;
import com.salesianostriana.dam.kiloapi.model.Aportacion;
import com.salesianostriana.dam.kiloapi.model.KilosDisponibles;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import com.salesianostriana.dam.kiloapi.repository.KilosDisponiblesRepository;
import com.salesianostriana.dam.kiloapi.service.AportacionService;
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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "KilosDisponibles", description = "Controlador para gestionar los kilos disponibles")
public class KilosDisponiblesController {
    private final KilosDisponiblesRepository kilosDisponiblesRepository;
    private final TipoAlimentoService tipoAlimentoService;

    private final AportacionService aportacionService;

    private final AportacionDtoConverter aportacionDtoConverter;

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
    @JsonView(TipoAlimentoViews.MostrarDisponible.class)
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
                            array = @ArraySchema(schema = @Schema(implementation = AportacionDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                [
                                                    {
                                                        "id": 15,
                                                        "fecha": "2022-12-12",
                                                        "aportaciones": [
                                                            {
                                                                "numLinea": 1,
                                                                "tipoAlimento": "Pasta",
                                                                "cantidadAportada": 2.0,
                                                                "kilosDisponibles": 12.0
                                                            },
                                                            {
                                                                "numLinea": 2,
                                                                "tipoAlimento": "Leche",
                                                                "cantidadAportada": 4.0,
                                                                "kilosDisponibles": 25.0
                                                            },
                                                            {
                                                                "numLinea": 3,
                                                                "tipoAlimento": "Lentejas",
                                                                "cantidadAportada": 1.0,
                                                                "kilosDisponibles": 6.0
                                                            }
                                                        ],
                                                        "listaAlimentos": {}
                                                    }
                                                ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado un tipo de alimento relacionado con ese ID",
                    content = @Content),
    })
    @GetMapping("/kilosDisponibles/{id}")
    public ResponseEntity<List<AportacionDto>> findById(@PathVariable Long id) {

        List<Aportacion> lista = aportacionService.getDetallesAportaciones(id);

        return lista.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.ok().body(lista.stream()
                        .map(aportacionDtoConverter::aportacionToGetAportacionDto2)
                        .collect(Collectors.toList()));

    }
}
