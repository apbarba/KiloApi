package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.dto.Caja.CajaDto;
import com.salesianostriana.dam.kiloapi.model.Caja;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import com.salesianostriana.dam.kiloapi.repository.CajaRepository;
import com.salesianostriana.dam.kiloapi.repository.DestinatarioRepository;
import com.salesianostriana.dam.kiloapi.repository.TipoAlimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class CajaService {

    private final CajaRepository repository;
    private final TipoAlimentoRepository alimentoRepository;

    private final DestinatarioRepository destinatarioRepository;

    public Caja add(Caja caja) {
        return repository.save(caja);
    }

    public Optional<Caja> findById(Long id) {
        return repository.findById(id);
    }

    public List<Caja> findAll() {
        return repository.findAll();
    }

    public Caja edit(Caja caja) {
        return repository.save(caja);
    }

    public void delete(Caja caja) {
        repository.delete(caja);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean comprobarDatos(Caja caja) {
        return caja.getQr() == "" || caja.getNumCaja() <= 0;
    }

    public void calcularKg(Caja c) {
        c.setKilosTotales(0);
        c.getTieneList().forEach(t -> {
            c.setKilosTotales(c.getKilosTotales() + t.getCantidadKgs());
        });
        this.edit(c);
    }

    public Optional<CajaDto> getCajaDto(Long id) {
        return repository.getCajaDto(id);

    }

    public CajaDto devolverCajaDto(Caja c) {
        CajaDto cdto = repository.generarCajaDto(c.getId());
        cdto.setAlimentos(alimentoRepository.generarListadoTipoAlimento(c.getId()));

        return cdto;
    }

    public CajaDto devolverCajaDestinatario(Caja c){
        CajaDto dto = devolverCajaDto(c);
        dto.setDestinatarioDto(destinatarioRepository.crearDestinatarioDto(c.getDestinatario().getId()));

        return dto;
    }

    public boolean comprobarExistenciaEnCaja(TipoAlimento t) {
        AtomicBoolean existe = new AtomicBoolean(false);
        this.findAll().forEach(c -> {
            c.getTieneList().forEach(tiene -> {
                if (tiene.getTipoAlimento().equals(t)) {
                    // Si existe, no se puede borrar
                    existe.set(true);
                }
            });
        });
        return existe.get();
    }

}
