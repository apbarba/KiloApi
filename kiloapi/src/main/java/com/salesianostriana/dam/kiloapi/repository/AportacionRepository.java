package com.salesianostriana.dam.kiloapi.repository;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.Aportacion.AportacionDto;
import com.salesianostriana.dam.kiloapi.dto.Aportacion.AportacionViews;
import com.salesianostriana.dam.kiloapi.dto.Aportacion.DetalleAportacion.DetalleAportacionDto;
import com.salesianostriana.dam.kiloapi.dto.Caja.CajaViews;
import com.salesianostriana.dam.kiloapi.model.Aportacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AportacionRepository extends JpaRepository<Aportacion, Long> {


    @Query("""
            select a from Aportacion a 
            where a.clase.id
             =:id
            """
    )
    List<Aportacion> findAportacionByClase(Long id);

    @Query("""
                 select new com.salesianostriana.dam.kiloapi.dto.Aportacion.AportacionDto(
                     a.id, c.nombre, c.tutor, a.fecha
                 )
                 from Aportacion a LEFT JOIN a.clase c
                 where a.id = :id           
            """)
    AportacionDto generarAportacionDto(@Param("id") Long id);

    @Query("""
                 select new com.salesianostriana.dam.kiloapi.dto.Aportacion.DetalleAportacion.DetalleAportacionDto(
                     d.detallesPK.numLinea_id, t.nombre, d.cantidadKg, 0.0
                 )
                 from Aportacion a LEFT JOIN a.detalleAportacionList d LEFT JOIN d.tipoAlimento t
                 where a.id = :id
            """)
    List<DetalleAportacionDto> generarListadoAportaciones(@Param("id") Long id);

    @Query("""
            select a from Aportacion a LEFT JOIN a.detalleAportacionList d 
            where d.tipoAlimento.id =:id
            """
    )
    List<Aportacion> getDetallesAportaciones(@Param("id") Long id);
}
