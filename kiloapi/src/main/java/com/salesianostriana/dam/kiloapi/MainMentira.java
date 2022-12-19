package com.salesianostriana.dam.kiloapi;

import com.salesianostriana.dam.kiloapi.controller.AportacionController;
import com.salesianostriana.dam.kiloapi.model.*;
import com.salesianostriana.dam.kiloapi.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MainMentira {


    private final CajaRepository cajaService;
    private final TipoAlimentoRepository tipoAlimentoService;

    private final TieneRepository tieneService;
    private final ClaseRepository claseRepository;

    private final AportacionRepository aportacionRepository;
    private final DestinatarioRepository destinatarioRepository;
    @PostConstruct
    public void test() {
        Clase clase = Clase.builder().tutor("lmlopez").nombre("2ºDAM").build();
        claseRepository.save(clase);


        TipoAlimento t1 = TipoAlimento.builder().nombre("Garabanzos").build();
        Caja c1 = Caja.builder().qr("eoooeooe").numCaja(3).kilosTotales(3.0).build();

        Tiene tiene = Tiene.builder().caja(c1).tipoAlimento(t1).build();
        Destinatario destinatario = Destinatario.builder().nombre("pedro").direccion("tucasa").build();
        destinatarioRepository.save(destinatario);
        cajaService.save(c1);
        tipoAlimentoService.save(t1);
        tieneService.save(tiene);
        }

    }
