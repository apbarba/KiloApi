package com.salesianostriana.dam.kiloapi.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Aportacion {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate fecha;

    @OneToMany(mappedBy = "aportacion")
    private List<Clase> clase = new ArrayList<>();

    @OneToMany(mappedBy = "aportaciones")
    private List<DetalleAportacion> detalleAportacionList = new ArrayList<>();


}
