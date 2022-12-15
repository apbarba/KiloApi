package com.salesianostriana.dam.kiloapi.repository;

import com.salesianostriana.dam.kiloapi.controller.AportacionController;
import com.salesianostriana.dam.kiloapi.model.Aportacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AportacionRepository extends JpaRepository <Aportacion, Long> {
}
