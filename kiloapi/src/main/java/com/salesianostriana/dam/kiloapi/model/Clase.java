package com.salesianostriana.dam.kiloapi.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Clase {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    private String tutor;

    @OneToMany( mappedBy = "clase",fetch = FetchType.EAGER,  cascade = CascadeType.ALL, orphanRemoval = false)
    @Builder.Default
    private List<Aportacion> aportacionList = new ArrayList<>();

}