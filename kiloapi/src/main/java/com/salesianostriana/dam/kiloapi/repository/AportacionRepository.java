package com.salesianostriana.dam.kiloapi.repository;

import com.salesianostriana.dam.kiloapi.model.Aportacion;
import com.salesianostriana.dam.kiloapi.model.Clase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AportacionRepository extends JpaRepository<Aportacion, Long> {

}
