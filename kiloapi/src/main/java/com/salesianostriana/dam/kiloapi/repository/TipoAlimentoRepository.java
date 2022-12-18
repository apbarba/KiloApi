package com.salesianostriana.dam.kiloapi.repository;

import com.salesianostriana.dam.kiloapi.dto.TipoAlimento.TipoAlimentoDto;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TipoAlimentoRepository extends JpaRepository<TipoAlimento, Long> {

    @Query("""
             select new com.salesianostriana.dam.kiloapi.dto.TipoAlimento.TipoAlimentoDto(
                 t.id, t.nombre, k.cantidadDisponible, 0.0
             )
             from TipoAlimento t LEFT JOIN t.kilosDisponibles k
             where t.id = :id
            """)
    TipoAlimentoDto detallesAlimento(@Param("id") Long id);

}
