package com.salesianostriana.dam.kiloapi.repository;

import com.salesianostriana.dam.kiloapi.model.Aportacion;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AportacionRepository extends JpaRepository <Aportacion, Long> {



    @Query("""
            select a from Aportacion a 
            where a.clase.id
             =:id
            """
    )
    List<Aportacion> findAportacionByClase(Long id);
}