package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import com.salesianostriana.dam.kiloapi.service.TipoAlimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TipoAlimentoController {

    private final TipoAlimentoService tipoAlimentoService;

    @Operation(summary = "Obtiene todos los tipos de alimentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado todas las clases",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TipoAlimento.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 1,
                                                    "nombre": "Latas de conserva"
                                                },
                                                {
                                                    "id": 2,
                                                    "nombre": "Productos limpieza"
                                                }
                                             ]                                         
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningun tipo de alimento",
                    content = @Content),
    })
    @GetMapping("/tipoAlimento/")
    public ResponseEntity<List<TipoAlimento>> getAll() {
        if (tipoAlimentoService.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(tipoAlimentoService.findAll());
        }
    }

    @Operation(summary = "Agrega un nuevo tipo de alimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha agregado el tipo de alimento",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TipoAlimento.class),
                            examples = {@ExampleObject(
                                    value = """
                                            
                                                {
                                                    "id": 1,
                                                    "nombre": "Latas de conserva"
                                                }
                                                                                      
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha agregado el tipo de alimento",
                    content = @Content),
    })
    @PostMapping("/tipoAlimento/")
    public ResponseEntity<TipoAlimento> newTipoAlimento(@RequestBody TipoAlimento tipoAlimento) {
        if (tipoAlimento.getNombre() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else
            return ResponseEntity.status(HttpStatus.CREATED).body(tipoAlimentoService.add(tipoAlimento));
    }
}
