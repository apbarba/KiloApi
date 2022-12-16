package com.salesianostriana.dam.kiloapi.repository;

import com.salesianostriana.dam.kiloapi.model.DetalleAportacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallesAnotacion extends JpaRepository<DetalleAportacion, Long> {
}
