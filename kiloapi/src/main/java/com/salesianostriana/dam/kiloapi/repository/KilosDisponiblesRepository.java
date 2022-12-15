package com.salesianostriana.dam.kiloapi.repository;

import com.salesianostriana.dam.kiloapi.model.KilosDisponibles;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KilosDisponiblesRepository extends JpaRepository<KilosDisponibles, TipoAlimento> {
}