package com.salesianostriana.dam.kiloapi.controller;

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

@RestController
@RequiredArgsConstructor
@Tag(name = "Clase", description = "Controlador para gestionar las clases")
public class ClaseController {
    private final ClaseService claseService;

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
                    array = @ArraySchema(schema = @Schema(implementation = Clase.class)),
                    examples = {@ExampleObject(value = """
                            [
                                {"id": 1, "nombre": "1ºDAM","tutor": "Miguel Campos"},
                                {"id": 2, "nombre": "2ºDAM","tutor": "Luis Miguel López Magaña"}
                            ]                                          
                            """)})}), @ApiResponse(responseCode = "404", description = "No se ha encontrado ninguna clase", content = @Content),})
    @GetMapping("/clase/")
    public ResponseEntity<List<Clase>> findAll() {
        List<Clase> claseList = claseService.findAll();
        return claseList.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build() :
                ResponseEntity.status(HttpStatus.OK).body(claseList);


    }

    @Operation(summary = "Edita las propiedades de una clase por ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "La clase ha sido modificada correctamente",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Clase.class))}),
            @ApiResponse(responseCode = "404", description = "No se ha encontrado ninguna clase relacionada con ese ID", content = @Content),})
    @PutMapping("/clase/{id}")
    public ResponseEntity<Clase> editClase(@RequestBody Clase clase, @PathVariable Long id) {
        Optional<Clase> c1 = claseService.findById(id);

        return c1.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.of(c1.map(old -> {
            old.setAportacionList(c1.get().getAportacionList());
            old.setNombre(c1.get().getNombre());
            old.setTutor(c1.get().getTutor());
            claseService.add(old);
            return old;
        }));

    }
}
