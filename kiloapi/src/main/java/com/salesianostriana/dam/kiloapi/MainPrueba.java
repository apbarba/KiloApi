package com.salesianostriana.dam.kiloapi;

import com.salesianostriana.dam.kiloapi.model.*;
import com.salesianostriana.dam.kiloapi.repository.*;
import com.salesianostriana.dam.kiloapi.service.CajaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MainPrueba {

    public final TipoAlimentoRepository tipoAlimentoRepository;
    public final KilosDisponiblesRepository kilosDisponiblesRepository;
    public final CajaService cajaService;
    public final CajaRepository cajaRepository;
    public final TieneRepository tieneRepository;
    public final DestinatarioRepository destinatarioRepository;
    private final ClaseRepository claseRepository;
    private final AportacionRepository aportacionRepository;

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
        TipoAlimento ta4 = TipoAlimento.builder()
                .nombre("Dodotis")
                .build();
        TipoAlimento ta5 = TipoAlimento.builder()
                .nombre("Turrón")
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
        KilosDisponibles kd4 = KilosDisponibles.builder()
                .cantidadDisponible(0)
                .build();
        KilosDisponibles kd5 = KilosDisponibles.builder()
                .cantidadDisponible(2)
                .build();

        Caja c1 = new Caja("http://www.caja1.com", 1);
        Caja c2 = new Caja("http://www.caja2.com", 2);
        Caja c3 = new Caja("http://www.caja3.com", 3);
        Caja c4 = new Caja("http://www.caja4.com", 4);

        ta1.addKilosToTipo(kd1);
        ta2.addKilosToTipo(kd2);
        ta3.addKilosToTipo(kd3);
        ta4.addKilosToTipo(kd4);
        ta5.addKilosToTipo(kd5);

        tipoAlimentoRepository.saveAll(List.of(ta1, ta2, ta3, ta4, ta5));

        kilosDisponiblesRepository.saveAll(List.of(kd1, kd2, kd3, kd4, kd5));

        cajaRepository.saveAll(List.of(c1, c2, c3, c4));

        TienePK tpk1 = new TienePK(ta1.getId(), c1.getId());
        TienePK tpk2 = new TienePK(ta2.getId(), c1.getId());
        TienePK tpk3 = new TienePK(ta4.getId(), c2.getId());
        TienePK tpk4 = new TienePK(ta3.getId(), c2.getId());
        TienePK tpk5 = new TienePK(ta1.getId(), c2.getId());
        TienePK tpk6 = new TienePK(ta2.getId(), c3.getId());
        TienePK tpk7 = new TienePK(ta3.getId(), c4.getId());

        Tiene t1 = Tiene.builder()
                .id(tpk1)
                .cantidadKgs(6)
                .build();
        Tiene t2 = Tiene.builder()
                .id(tpk2)
                .cantidadKgs(2)
                .build();
        Tiene t3 = Tiene.builder()
                .id(tpk3)
                .cantidadKgs(1)
                .build();
        Tiene t4 = Tiene.builder()
                .id(tpk4)
                .cantidadKgs(5)
                .build();
        Tiene t5 = Tiene.builder()
                .id(tpk5)
                .cantidadKgs(3)
                .build();
        Tiene t6 = Tiene.builder()
                .id(tpk6)
                .cantidadKgs(4)
                .build();
        Tiene t7 = Tiene.builder()
                .id(tpk7)
                .cantidadKgs(1)
                .build();

        ta1.addTipoToTiene(t1);
        c1.addTieneToCaja(t1);

        ta2.addTipoToTiene(t2);
        c1.addTieneToCaja(t2);

        ta4.addTipoToTiene(t3);
        c2.addTieneToCaja(t3);

        ta3.addTipoToTiene(t4);
        c2.addTieneToCaja(t4);

        ta1.addTipoToTiene(t5);
        c2.addTieneToCaja(t5);

        ta2.addTipoToTiene(t6);
        c3.addTieneToCaja(t6);

        ta3.addTipoToTiene(t7);
        c4.addTieneToCaja(t7);

        cajaService.calcularKg(c1);
        cajaService.calcularKg(c2);
        cajaService.calcularKg(c3);
        cajaService.calcularKg(c4);

        tieneRepository.saveAll(List.of(t1, t2, t3, t4, t5, t6, t7));

        Destinatario d1 = Destinatario.builder()
                .nombre("Banco Alimentos Triana")
                .direccion("C/ Castilla, 10")
                .personaContacto("Antonio Álvarez")
                .telefono("123456789")
                .build();
        Destinatario d2 = Destinatario.builder()
                .nombre("Banco Alimentos Mairena")
                .direccion("C/Ciaurriz")
                .personaContacto("Bartolomé Méndez")
                .telefono("987654321")
                .build();
        Destinatario d3 = Destinatario.builder()
                .nombre("Banco Alimentos Sevilla")
                .direccion("Avda/Coria")
                .personaContacto("José Pérez")
                .telefono("456789123")
                .build();

        c1.addCajaToDestinatario(d1);
        c2.addCajaToDestinatario(d1);
        c3.addCajaToDestinatario(d2);
        c4.addCajaToDestinatario(d3);

        destinatarioRepository.saveAll(List.of(d1, d2, d3));

        cajaService.edit(c1);
        cajaService.edit(c2);
        cajaService.edit(c3);
        cajaService.edit(c4);

        Clase cl1 = Clase.builder()
                .nombre("1ºDAM")
                .tutor("Miguel Campos")
                .build();
        Clase cl2 = Clase.builder()
                .nombre("2ºDAM")
                .tutor("Luismi López")
                .build();

        claseRepository.saveAll(List.of(cl1, cl2));

        Aportacion a1 = Aportacion.builder()
                .fecha(LocalDate.of(2022, 12, 12))
                .build();
        Aportacion a2 = Aportacion.builder()
                .fecha(LocalDate.of(2022, 12, 14))
                .build();
        Aportacion a3 = Aportacion.builder()
                .fecha(LocalDate.of(2022, 12, 15))
                .build();
        Aportacion a4 = Aportacion.builder()
                .fecha(LocalDate.of(2022, 12, 16))
                .build();

        a1.addAportacionToClase(cl1);
        a2.addAportacionToClase(cl2);
        a3.addAportacionToClase(cl1);
        a4.addAportacionToClase(cl2);

        aportacionRepository.saveAll(List.of(a1, a2, a3, a4));

        DetallesPK det1 = new DetallesPK(a1.getId(), 1);
        DetallesPK det2 = new DetallesPK(a1.getId(), 2);
        DetallesPK det3 = new DetallesPK(a1.getId(), 3);
        DetallesPK det4 = new DetallesPK(a2.getId(), 1);
        DetallesPK det5 = new DetallesPK(a2.getId(), 2);
        DetallesPK det6 = new DetallesPK(a3.getId(), 1);

        DetalleAportacion da1 = DetalleAportacion.builder()
                .detallesPK(det1)
                .cantidadKg(2)
                .tipoAlimento(ta1)
                .build();
        DetalleAportacion da2 = DetalleAportacion.builder()
                .detallesPK(det2)
                .cantidadKg(4)
                .tipoAlimento(ta2)
                .build();
        DetalleAportacion da3 = DetalleAportacion.builder()
                .detallesPK(det3)
                .cantidadKg(1)
                .tipoAlimento(ta3)
                .build();
        DetalleAportacion da4 = DetalleAportacion.builder()
                .detallesPK(det4)
                .cantidadKg(2)
                .tipoAlimento(ta1)
                .build();
        DetalleAportacion da5 = DetalleAportacion.builder()
                .detallesPK(det5)
                .cantidadKg(4)
                .tipoAlimento(ta2)
                .build();
        DetalleAportacion da6 = DetalleAportacion.builder()
                .detallesPK(det6)
                .cantidadKg(1)
                .tipoAlimento(ta3)
                .build();
        DetalleAportacion da7 = DetalleAportacion.builder()
                .detallesPK(det6)
                .cantidadKg(0)
                .tipoAlimento(ta4)
                .build();

        a1.addDetalleAportacion(da1);
        a1.addDetalleAportacion(da2);
        a1.addDetalleAportacion(da3);
        a2.addDetalleAportacion(da4);
        a2.addDetalleAportacion(da5);
        a3.addDetalleAportacion(da6);
        a4.addDetalleAportacion(da7);

        aportacionRepository.saveAll(List.of(a1, a2, a3, a4));

    }
}
