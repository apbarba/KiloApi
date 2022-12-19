package com.salesianostriana.dam.kiloapi.repository;

import com.salesianostriana.dam.kiloapi.dto.TipoAlimento.TipoAlimentoDto;
import com.salesianostriana.dam.kiloapi.model.Caja;
import com.salesianostriana.dam.kiloapi.model.Tiene;
import com.salesianostriana.dam.kiloapi.model.TienePK;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TieneRepository extends JpaRepository<Tiene, TienePK> {

    Optional<Tiene> findByCajaAndTipoAlimento(Caja caja, TipoAlimento tipoAlimento);
}
