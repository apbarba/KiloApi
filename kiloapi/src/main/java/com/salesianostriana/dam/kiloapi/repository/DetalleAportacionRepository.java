package com.salesianostriana.dam.kiloapi.repository;

import com.salesianostriana.dam.kiloapi.model.DetalleAportacion;
import com.salesianostriana.dam.kiloapi.model.DetallesPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleAportacionRepository extends JpaRepository<DetalleAportacion, Long> {
}
