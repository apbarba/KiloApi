package com.salesianostriana.dam.kiloapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesianostriana.dam.kiloapi.dto.cajaDto.CajaDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Caja {

    @Id
    @GeneratedValue
    private Long id;

    private String qr;

    private int numCaja;

    private double kilosTotales;

    @JsonIgnore
    @OneToMany(mappedBy = "caja", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Tiene> tieneList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "destinatario_id", foreignKey = @ForeignKey(name = "FK_CAJA_DESTINATARIO"))
    private Destinatario destinatario;


    // CONSTRUCTOR
    public Caja(String qr, int numCaja) {
        this.qr = qr;
        this.numCaja = numCaja;
    }

    // ESTE CONSTRUCTOR LO QUITAREMOS CUANDO CREEMOS LOS JSONVIEW
    public Caja(Long id, String qr, int numCaja) {
        this.id = id;
        this.qr = qr;
        this.numCaja = numCaja;
    }

    public static Caja of (CajaDto cajaDto) {
        return new Caja(
                cajaDto.getId(),
                cajaDto.getQr(),
                cajaDto.getNumCaja()
        );
    }

    // MÉTODOS HELPERS GESTIÓN CAJA-DESTINATARIO
    public void addCajaToDestinatario(Destinatario d) {
        this.destinatario = d;
        d.getCajaList().add(this);
    }

    public void removeCajaFromDestinatario(Destinatario d) {
        this.destinatario = null;
        d.getCajaList().remove(this);
    }


    // MÉTODOS HELPERS GESTIÓN CAJA-TIENE
    public void addTieneToCaja(Tiene t) {
        t.setCaja(this);
        this.tieneList.add(t);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Caja caja = (Caja) o;
        return id.equals(caja.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Caja{" +
                "id=" + id +
                ", qr='" + qr + '\'' +
                ", numCaja=" + numCaja +
                ", kilosTotales=" + kilosTotales +
                //", tieneList=" + tieneList +
                ", destinatario=" + destinatario +
                '}';
    }

}
