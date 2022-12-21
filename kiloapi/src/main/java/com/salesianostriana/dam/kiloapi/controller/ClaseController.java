package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.dto.Caja.CajaDto;
import com.salesianostriana.dam.kiloapi.dto.Clase.ClaseDto;
import com.salesianostriana.dam.kiloapi.dto.Clase.ClaseDtoConverter;
import com.salesianostriana.dam.kiloapi.model.Clase;
import com.salesianostriana.dam.kiloapi.service.ClaseService;
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
@Tag(name = "Clase", description = "Controlador para gestionar las clases")
public class ClaseController {
    private final ClaseService claseService;

    private final ClaseDtoConverter claseDtoConverter;


    @Operation(summary = "Borra una clase en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la clase",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Clase.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la clase por el ID",
                    content = @Content),
    })
    @DeleteMapping("/clase/{id}")
    public ResponseEntity<Clase> deleteClase(@PathVariable Long id) {
        if (claseService.findById(id).isPresent()) {
            claseService.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtiene todas las clases")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Se han encontrado clases",
            content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ClaseDto.class)),
                    examples = {@ExampleObject(value = """
                                                            [
                                                                {
                                                                    "id": 13,
                                                                    "nombre": "1ºDAM",
                                                                    "tutor": "Miguel Campos",
                                                                    "numAportaciones": 2
                                                                },
                                                                {
                                                                    "id": 14,
                                                                    "nombre": "2ºDAM",
                                                                    "tutor": "Luismi López",
                                                                    "numAportaciones": 2
                                                                }
                                                            ]                                       
                            """)})}), @ApiResponse(responseCode = "404", description = "No se ha encontrado ninguna clase", content = @Content),})
    @GetMapping("/clase/")
    public ResponseEntity<List<ClaseDto>> findAll() {
        List<ClaseDto> claseList = claseService.findAll()
                .stream().map(claseDtoConverter::claseToGetClaseDto).toList();
        return claseList.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(claseList);


    }

    @Operation(summary = "Crear una nueva clase")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Clase creado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Clase.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {"id": 1, "nombre": "2DAM", "Tutor": ""Luismi}
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "DATOS ERRÓNEOS",
                    content = @Content)})
    @PostMapping("/clase/")
    public ResponseEntity<Clase> newClase(@RequestBody Clase clase) {

        if (clase.getNombre().isEmpty() && clase.getTutor().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .build();
        } else {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(claseService.add(clase));
        }
    }

    @Operation(summary = "Encuenta la clase solicitada con sus aportaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la clase",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CajaDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "nombre": "2ºDAM",
                                                    "tutor": "Luis Miguel",
                                                    "totalKilos": 6.0,
                                                    "numAportaciones": 3
                                                }
                                             """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la clase",
                    content = @Content),
    })
    @GetMapping("/clase/{id}")
    public ResponseEntity<ClaseDto> findById(@PathVariable Long id) {

        Optional<ClaseDto> claseDto = claseService.getClaseById(id);

        if (claseService.findById(id).isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(claseDto.get());
        }
    }

    @Operation(summary = "Edita las propiedades de una clase por ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "La clase ha sido modificada correctamente",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClaseDto.class),
            examples = {@ExampleObject(value = """
                                                {
                                                    "id": 14,
                                                    "nombre": "2ºDAM",
                                                    "tutor": "Luis Miguel",
                                                    "numAportaciones": 3
                                                }
                                                """
            )})}),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado ninguna clase relacionada con ese ID", content = @Content),})
    @PutMapping("/clase/{id}")
    public ResponseEntity<ClaseDto> editClase(@RequestBody ClaseDto clase, @PathVariable Long id) {

        Optional<Clase> c1 = claseService.findById(id);


        if (c1.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } else {
            c1.get().setNombre(clase.getNombre());
            c1.get().setTutor(clase.getTutor());
            claseService.add(c1.get());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(claseDtoConverter.claseToGetClaseDto(c1.get()));
        }

    }
}
