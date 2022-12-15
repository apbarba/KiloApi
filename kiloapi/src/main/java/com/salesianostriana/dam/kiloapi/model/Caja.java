package com.salesianostriana.dam.kiloapi.model;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class Caja {

    @Id
    @GeneratedValue
    private Long id;

    private String qr;

    private int numCaja;

    // Se generan sumando de la lista de Tiene
    private double kilosTotales;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "caja", fetch = FetchType.EAGER)
    private List<Tiene> tieneList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "destinatario_id", foreignKey = @ForeignKey(name = "FK_CAJA_DESTINATARIO"))
    private Destinatario destinatario;

    // CONSTRUCTOR
    public Caja(String qr, int numCaja) {
        this.qr = qr;
        this.numCaja = numCaja;
    }
}
