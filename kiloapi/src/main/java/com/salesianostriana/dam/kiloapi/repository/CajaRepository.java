package com.salesianostriana.dam.kiloapi.repository;

import com.salesianostriana.dam.kiloapi.model.Caja;
import com.salesianostriana.dam.kiloapi.model.Destinatario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CajaRepository extends JpaRepository<Caja, Long> {

    //Consulta para recuperar las cajas asignadas a un destinatario(id)
   // @Query("select c from Caja c where c.destinatario = :destinatario")
   // List<Caja> findByDestinatario(@Param("destinatario")Destinatario destinatario);
}
