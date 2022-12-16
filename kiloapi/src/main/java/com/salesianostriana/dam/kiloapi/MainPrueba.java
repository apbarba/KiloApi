package com.salesianostriana.dam.kiloapi;

import com.salesianostriana.dam.kiloapi.model.*;
import com.salesianostriana.dam.kiloapi.repository.CajaRepository;
import com.salesianostriana.dam.kiloapi.repository.KilosDisponiblesRepository;
import com.salesianostriana.dam.kiloapi.repository.TieneRepository;
import com.salesianostriana.dam.kiloapi.repository.TipoAlimentoRepository;
import com.salesianostriana.dam.kiloapi.service.CajaService;
import com.salesianostriana.dam.kiloapi.service.KilosDisponiblesService;
import com.salesianostriana.dam.kiloapi.service.TieneService;
import com.salesianostriana.dam.kiloapi.service.TipoAlimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MainPrueba {


    public final TipoAlimentoService tipoAlimentoService;
    public final TipoAlimentoRepository tipoAlimentoRepository;

    public final KilosDisponiblesService kilosDiposniblesService;
    public final KilosDisponiblesRepository kilosDisponiblesRepository;

    public final CajaService cajaService;
    public final CajaRepository cajaRepository;

    public final TieneService tieneService;
    public final TieneRepository tieneRepository;

    @PostConstruct
    public void run() {

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

        Caja c1 = new Caja("http://www.caja1.com", 1);
        Caja c2 = new Caja("http://www.caja2.com", 2);
        Caja c3 = new Caja("http://www.caja3.com", 3);

        ta1.addKilosToTipo(kd1);
        ta2.addKilosToTipo(kd2);
        ta3.addKilosToTipo(kd3);

        tipoAlimentoRepository.saveAll(List.of(ta1, ta2, ta3));

        kilosDisponiblesRepository.saveAll(List.of(kd1, kd2, kd3));

        cajaRepository.saveAll(List.of(c1, c2, c3));

        TienePK tpk1 = new TienePK(ta1.getId(), c1.getId());
        TienePK tpk2 = new TienePK(ta2.getId(), c1.getId());

        Tiene t1 = Tiene.builder()
                .id(tpk1)
                .cantidadKgs(6)
                .build();
        Tiene t2 = Tiene.builder()
                .id(tpk2)
                .cantidadKgs(2)
                .build();

        ta1.addTipoToTiene(t1);
        c1.addTieneToCaja(t1);

        ta2.addTipoToTiene(t2);
        c1.addTieneToCaja(t2);

        c1.setKilosTotales(t1.getCantidadKgs() + t2.getCantidadKgs());
        cajaService.edit(c1);

        tieneRepository.saveAll(List.of(t1, t2));

    }
}