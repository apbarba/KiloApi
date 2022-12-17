package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.repository.DestinatarioRepository;
import com.salesianostriana.dam.kiloapi.service.DestinatarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DestinatarioController {

    private final DestinatarioService destinatarioService;

    private final DestinatarioRepository destinatarioRepository;
}
