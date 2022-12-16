package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.service.TipoAlimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;

@Controller
@RequiredArgsConstructor
public class TipoAlimentoController {

    private final TipoAlimentoService tipoAlimentoService;



}
