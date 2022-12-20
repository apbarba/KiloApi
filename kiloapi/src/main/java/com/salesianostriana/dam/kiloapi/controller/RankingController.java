package com.salesianostriana.dam.kiloapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.Clase.ClaseDto;
import com.salesianostriana.dam.kiloapi.dto.Clase.ClaseViews;
import com.salesianostriana.dam.kiloapi.service.ClaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Ranking", description = "Controlador para gestionar el ranking de las aportaciones")
public class RankingController {

    private final ClaseService claseService;
    @JsonView(ClaseViews.Master.class)
    @GetMapping("/ranking/")
    public ResponseEntity<List<ClaseDto>> getRanking() {
        List<ClaseDto> ranking = claseService.getRanking();
        return ranking.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).body(ranking);

    }
}