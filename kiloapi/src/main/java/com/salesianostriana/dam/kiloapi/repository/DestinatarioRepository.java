package com.salesianostriana.dam.kiloapi.repository;

import com.salesianostriana.dam.kiloapi.dto.Destinatario.DestinatarioDto;
import com.salesianostriana.dam.kiloapi.model.Destinatario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DestinatarioRepository extends JpaRepository<Destinatario, Long> {

    @Query("select dto.nombre, dto.direccion, dto.personaContacto, dto.telefono, c.kilosTotales from Destinatario dto JOIN Caja c On (dto.id = c.destinatario) where c.destinatario.id = :id")
    public List<DestinatarioDto> getDestinatario(@Param("id") Long id);

    @Query("select new com.salesianostriana.dam.kiloapi.dto.Destinatario.DestinatarioDto(d. id, d.nombre, d.direccion, d.personaContacto, d.telefono) " +
            "from Destinatario d where d.id = :id")
    DestinatarioDto crearDestinatarioDto(@Param("id") Long id);

    @Query("select new com.salesianostriana.dam.kiloapi.dto.Destinatario.DestinatarioDto(d.id, d.nombre, d.direccion, d.personaContacto, d.telefono, sum(c.kilosTotales)) " +
            "from Destinatario d LEFT JOIN Caja c on c.destinatario = d where d.id = :id " +
            "group by d.id")
    DestinatarioDto crearDtoKgTotales(@Param("id") Long id);

    @Query("select c.numCaja from Caja c where c.destinatario.id = :id")
    List<Integer> crearListaNumCajas(@Param("id") Long id);

}


