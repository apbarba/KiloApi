package com.salesianostriana.dam.kiloapi.repository;

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
            select a from Aportacion a LEFT JOIN a.detalleAportacionList d
            where d.tipoALimento.id = : id
            """)
    List<Aportacion> getDetallesAportaciones(@Param("id") Long id);
}