package com.salesianostriana.dam.kiloapi.repository;

import com.salesianostriana.dam.kiloapi.dto.Aportacion.DetalleAportacion.DetalleAportacionDto;
import com.salesianostriana.dam.kiloapi.dto.Caja.CajaDto;
import com.salesianostriana.dam.kiloapi.model.Aportacion;
import com.salesianostriana.dam.kiloapi.model.DetalleAportacion;
import com.salesianostriana.dam.kiloapi.model.KilosDisponibles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AportacionRepository extends JpaRepository<Aportacion, Long> {



    @Query("""
            select a from Aportacion a 
            where a.clase.id
             =:id
            """
    )
    List<Aportacion> findAportacionByClase(Long id);


    @Query("""
            select a from Aportacion a LEFT JOIN a.detalleAportacionList d 
            where d.tipoAlimento.id =:id
            """
    )
    List<Aportacion> getDetallesAportaciones(@Param("id") Long id);
}