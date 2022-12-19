package com.salesianostriana.dam.kiloapi.repository;

import com.salesianostriana.dam.kiloapi.model.Destinatario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DestinatarioRepository extends JpaRepository<Destinatario, Long> {

    @Query("select dto.nombre, dto.direccion, dto.personaContacto, dto.telefono, c.kilosT from Destinatario dto JOIN Caja c On (dto.id = c.destinatario) where c.destinatario = idDestinatario")
    public List<GetDestinatario> getDestinatario(@Param("idDestinatario") Long id);

    @Query("select new com.salesianostriana.dam.kiloapi.dto.Destinatario.DestinatarioDto(d. id, d.nombre, d.direccion, d.personaContacto, d.telefono) " +
            "from Destinatario d where d.id = :id")
    DestinatarioDto crearDestinatarioDto(@Param("id") Long id);
}


