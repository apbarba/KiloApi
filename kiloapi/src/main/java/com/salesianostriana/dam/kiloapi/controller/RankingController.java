package com.salesianostriana.dam.kiloapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.Clase.ClaseDto;
import com.salesianostriana.dam.kiloapi.dto.Clase.ClaseViews;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Ranking", description = "Controlador para gestionar el ranking de las aportaciones")
public class RankingController {

    private final ClaseService claseService;

    @Operation(summary = "Obtiene una lista clases ordenadas por aportaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado todas las clases",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ClaseDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                [
                                                    {
                                                        "nombre": "1ºDAM",
                                                        "totalKilos": 7.0,
                                                        "numAportaciones": 4,
                                                        "media": 2.33
                                                    },
                                                    {
                                                        "nombre": "2ºDAM",
                                                        "totalKilos": 6.0,
                                                        "numAportaciones": 3,
                                                        "media": 2.0
                                                    }
                                                ]                                 
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna clase",
                    content = @Content),
    })
    @JsonView(ClaseViews.Ranking.class)
    @GetMapping("/ranking/")
    public ResponseEntity<List<ClaseDto>> getRanking() {
        List<ClaseDto> ranking = claseService.getRanking();
        for (int i = 0; i < ranking.size(); i++) {
            ranking.get(i).setPosicion(i+1);
        }
        return ranking.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).body(ranking);

    }
}