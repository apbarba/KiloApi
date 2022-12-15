package com.salesianostriana.dam.kiloapi.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class KilosDisponibles {

    @Id
    @GeneratedValue
    private Long id;
  /* @OneToOne
    @JoinColumn(name = "tipo_alimento_id")
    @Id
    private TipoAlimento tipoAlimentoId;
    private Double cantidadDisponible;*/
}
