package com.salesianostriana.dam.kiloapi;

import com.salesianostriana.dam.kiloapi.repository.ClaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
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

        cajaService.calcularKg(c1);

        tieneRepository.saveAll(List.of(t1, t2));

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

        destinatarioRepository.saveAll(List.of(d1, d2, d3));

        cajaService.edit(c1);
        cajaService.edit(c2);
        cajaService.edit(c3);

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

        a1.addAportacionToClase(cl1);
        a2.addAportacionToClase(cl2);
        a3.addAportacionToClase(cl1);

        aportacionRepository.saveAll(List.of(a1, a2, a3));

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

        a1.addDetalleAportacion(da1);
        a1.addDetalleAportacion(da2);
        a1.addDetalleAportacion(da3);
        a2.addDetalleAportacion(da4);
        a2.addDetalleAportacion(da5);
        a3.addDetalleAportacion(da6);

        aportacionRepository.saveAll(List.of(a1, a2, a3));
    }
}
