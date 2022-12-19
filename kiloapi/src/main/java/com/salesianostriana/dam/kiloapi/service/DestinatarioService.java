package com.salesianostriana.dam.kiloapi.service;

import com.salesianostriana.dam.kiloapi.model.Destinatario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DestinatarioService {

    public boolean comprobarDatos(Destinatario d) {
        return d.getNombre() == "" || d.getDireccion() == "" || d.getPersonaContacto() == "" || d.getTelefono() == "";
    }

}
