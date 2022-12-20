package com.salesianostriana.dam.kiloapi.repository;

import com.salesianostriana.dam.kiloapi.dto.Clase.ClaseDto;
import com.salesianostriana.dam.kiloapi.model.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClaseRepository extends JpaRepository<Clase, Long> {
    @Query("""
            SELECT NEW com.salesianostriana.dam.kiloapi.dto.Clase.ClaseDto
            (c.nombre, COUNT(a), SUM(d.cantidadKg), ROUND(AVG(d.cantidadKg),2))
            FROM Clase c LEFT JOIN c.aportacionList a LEFT JOIN a.detalleAportacionList d
            GROUP BY c.nombre
            ORDER BY SUM(d.cantidadKg) DESC
            """
    )
    List<ClaseDto> getRanking();

    @Query("""
            SELECT NEW com.salesianostriana.dam.kiloapi.dto.Clase.ClaseDto
            (c.nombre, c.tutor, SUM(d.cantidadKg),COUNT(a))
            FROM Clase c LEFT JOIN c.aportacionList a LEFT JOIN a.detalleAportacionList d
            WHERE c.id = :id
            """
    )
    Optional<ClaseDto> getClaseById(@Param("id")Long id);
}
