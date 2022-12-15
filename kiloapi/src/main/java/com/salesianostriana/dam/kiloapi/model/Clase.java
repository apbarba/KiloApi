package com.salesianostriana.dam.kiloapi.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Clase {
    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String tutor;

    @ManyToOne
    private Aportacion aportacion;

}
