package com.salesianostriana.dam.kiloapi.repository;

import com.salesianostriana.dam.kiloapi.model.Caja;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TipoAlimentoRepository extends JpaRepository<TipoAlimento, Long> {

    //Esta consulta se realiza para recuperar los alimentos que tiene una caja(id)
   // @Query("select ta from TipoAlimento ta where ta In (select ta from Tiene t where t.caja = :idCaja)")
   // List<TipoAlimento> finByCaja(@Param("idCaja")Caja caja);
}
