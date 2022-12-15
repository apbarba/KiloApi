package com.salesianostriana.dam.kiloapi;

import com.salesianostriana.dam.kiloapi.model.KilosDisponibles;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import com.salesianostriana.dam.kiloapi.service.CajaService;
import com.salesianostriana.dam.kiloapi.service.KilosDisponiblesService;
import com.salesianostriana.dam.kiloapi.service.TieneService;
import com.salesianostriana.dam.kiloapi.service.TipoAlimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class MainPrueba {


    public final TipoAlimentoService tipoAlimentoService;
    public final KilosDisponiblesService kilosDiposniblesService;
    public final CajaService cajaService;
    public final TieneService tieneService;

    @PostConstruct
    public void run() {

        // AL CREAR UN TIPO DE ALIMENTO, SE TIENE QUE CREAR UNA INSTANCIA DE LOS KILOS DISPONIBLES A TRAVÉS DEL ID-TIPOALIMENTO
        TipoAlimento ta1 = TipoAlimento.builder()
                .nombre("Pasta")
                .build();
        TipoAlimento ta2 = TipoAlimento.builder()
                .nombre("Leche")
                .build();
        TipoAlimento ta3 = TipoAlimento.builder()
                .nombre("Lentejas")
                .build();

        KilosDisponibles kd1 = KilosDisponibles.builder()
                .cantidadDisponible(12)
                .build();
        KilosDisponibles kd2 = KilosDisponibles.builder()
                .cantidadDisponible(25)
                .build();
        KilosDisponibles kd3 = KilosDisponibles.builder()
                .cantidadDisponible(6)
                .build();

        ta1.addKilosToTipo(kd1);
        ta2.addKilosToTipo(kd2);
        ta3.addKilosToTipo(kd3);

        tipoAlimentoService.add(ta1);
        tipoAlimentoService.add(ta2);
        tipoAlimentoService.add(ta3);

        kilosDiposniblesService.add(kd1);
        kilosDiposniblesService.add(kd2);
        kilosDiposniblesService.add(kd3);

    }
}