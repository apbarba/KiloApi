package com.salesianostriana.dam.kiloapi;

import com.salesianostriana.dam.kiloapi.model.Caja;
import com.salesianostriana.dam.kiloapi.model.Destinatario;
import com.salesianostriana.dam.kiloapi.model.Tiene;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import com.salesianostriana.dam.kiloapi.repository.CajaRepository;
import com.salesianostriana.dam.kiloapi.repository.DestinatarioRepository;
import com.salesianostriana.dam.kiloapi.repository.TieneRepository;
import com.salesianostriana.dam.kiloapi.repository.TipoAlimentoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class MainMentira {


    private final CajaRepository cajaService;
    private final TipoAlimentoRepository tipoAlimentoService;

    private final TieneRepository tieneService;

    private final DestinatarioRepository destinatarioRepository;
    @PostConstruct
    public void test() {

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
