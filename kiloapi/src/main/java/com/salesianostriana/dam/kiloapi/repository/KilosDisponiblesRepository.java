package com.salesianostriana.dam.kiloapi.repository;

import com.salesianostriana.dam.kiloapi.dto.TipoAlimento.TipoAlimentoDto;
import com.salesianostriana.dam.kiloapi.model.KilosDisponibles;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KilosDisponiblesRepository extends JpaRepository<KilosDisponibles, TipoAlimento> {
    @Query("select new com.salesianostriana.dam.kiloapi.dto.TipoAlimento." +
            "TipoAlimentoDto(t.id, t.nombre, k.cantidadDisponible, 0.0)from TipoAlimento t JOIN t.kilosDisponibles k")
    List<TipoAlimentoDto> crearTipoAlimentoDto();


}