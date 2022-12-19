package com.salesianostriana.dam.kiloapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.Destinatario.DestinatarioDto;
import com.salesianostriana.dam.kiloapi.dto.Destinatario.DestinatarioViews;
import com.salesianostriana.dam.kiloapi.model.Destinatario;
import com.salesianostriana.dam.kiloapi.repository.DestinatarioRepository;
import com.salesianostriana.dam.kiloapi.service.DestinatarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Destinatario", description = "Controlador para gestionar los destinatarios")
public class DestinatarioController {

    private final DestinatarioService destinatarioService;

    private final DestinatarioRepository destinatarioRepository;

    @Operation(summary = "Agrega un nuevo destinatario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha agregado el destinatario",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Destinatario.class),
                            examples = {@ExampleObject(
                                    value = """
                                                                                        
                                                {
                                                    "id": 12,
                                                    "nombre": "Asociación 3000 viviendas",
                                                    "direccion": "Avenida Diputación",
                                                    "personaContacto": "Miguel Campos",
                                                    "telefono": "954954954",
                                                    "cajasList": []
                                                }
                                                                                      
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha agregado el destinatario",
                    content = @Content),
    })
    @PostMapping("/destinatario/")
    public ResponseEntity<DestinatarioDto> newClase(@RequestBody Destinatario destinatario) {
        if (destinatario.getNombre() == null || destinatario.getDireccion() == null ||
                destinatario.getPersonaContacto() == null || destinatario.getTelefono() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        destinatarioService.add(destinatario);
        return ResponseEntity.status(HttpStatus.CREATED).body(destinatarioRepository.crearDestinatarioDto(destinatario.getId()));
    }

    @Operation(summary = "Modificar un destinatario, buscado por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Destinatario modificado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DestinatarioDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 8,
                                                    "nombre": "Banco Alimentos Coria",
                                                    "direccion": "C/Mairena",
                                                    "personaContacto": "Bartolomé Méndez",
                                                    "telefono": "987654321",
                                                    "kgTotales": 0.0,
                                                    "numCajas": [
                                                        3
                                                    ]
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Cuerpo para la modificación aportado inválido",
                    content = @Content)})
    @JsonView(DestinatarioViews.Master.class)
    @PutMapping("/destinatario/{id}")
    public ResponseEntity<DestinatarioDto> actualizarDestinatario(@PathVariable Long id, @RequestBody Destinatario d) {
        Optional<Destinatario> destinatario = destinatarioService.findById(id);
        if (!destinatario.isPresent() || destinatarioService.comprobarDatos(d)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.of(
                    destinatario.map(des -> {
                        des.setNombre(d.getNombre());
                        des.setDireccion(d.getDireccion());
                        des.setPersonaContacto(d.getPersonaContacto());
                        des.setTelefono(d.getTelefono());
                        destinatarioService.edit(des);

                        return DestinatarioDto.of(des);
                    }));
        }
    }
    @Operation(summary = "Elimina un destinatario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "El destinatario ha sido eliminado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Destinatario.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra un destinatario con este ID",
                    content = @Content),
    })
    @DeleteMapping("/destinatario/{id}")
    public ResponseEntity<Destinatario> delete(@PathVariable Long id) {
        Optional<Destinatario> d1 = destinatarioService.findById(id);
        return d1.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @Operation(summary = "Obtiene la información de un destinatario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el destinatario relacionado con ese ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Destinatario.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado un destinatario con ese ID",
                    content = @Content),
    })
    @GetMapping("/destinatario/{id}")
    public ResponseEntity<Destinatario> findById(@PathVariable Long id) {
        Optional<Destinatario> d1 = destinatarioService.findById(id);
        return d1.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.of(destinatarioService.findById(id));

    }


}
