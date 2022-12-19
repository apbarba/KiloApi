package com.salesianostriana.dam.kiloapi.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Aportacion {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate fecha;

    @ManyToOne
    private Clase clase;

    @OneToMany(mappedBy = "aportacion",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<DetalleAportacion> detalleAportacionList = new ArrayList<>();

}