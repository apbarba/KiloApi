package com.salesianostriana.dam.kiloapi.repository;

import com.salesianostriana.dam.kiloapi.dto.Caja.CajaDto;
import com.salesianostriana.dam.kiloapi.model.Caja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CajaRepository extends JpaRepository<Caja, Long> {
    @Query("""
            select new com.salesianostriana.dam.kiloapi.dto.Caja.CajaDto(
                c.qr, c.numCaja, c.kilosTotales,d.id,d.nombre
            )
            from Caja c LEFT JOIN c.destinatario d 
            where c.id =:id
            """
    )
    Optional<CajaDto> getCajaDto(@Param("id") Long id);

}
