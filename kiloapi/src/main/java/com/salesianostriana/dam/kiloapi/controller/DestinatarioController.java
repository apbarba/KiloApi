package com.salesianostriana.dam.kiloapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.Caja.CajaDto;
import com.salesianostriana.dam.kiloapi.dto.Caja.CajaDtoConverter;
import com.salesianostriana.dam.kiloapi.dto.Destinatario.DestinatarioDto;
import com.salesianostriana.dam.kiloapi.dto.Destinatario.DestinatarioViews;
import com.salesianostriana.dam.kiloapi.dto.TipoAlimento.TipoAlimentoDto;
import com.salesianostriana.dam.kiloapi.model.Destinatario;
import com.salesianostriana.dam.kiloapi.repository.DestinatarioRepository;
import com.salesianostriana.dam.kiloapi.service.CajaService;
import com.salesianostriana.dam.kiloapi.service.DestinatarioService;
import com.salesianostriana.dam.kiloapi.service.TipoAlimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                            schema = @Schema(implementation = DestinatarioDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 19,
                                                    "nombre": "Banco Alimentos Salesianos",
                                                    "direccion": "C/Condes de Bustillos",
                                                    "personaContacto": "Paco Pepe P??rez",
                                                    "telefono": "654987321"
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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(destinatarioRepository.crearDestinatarioDto(destinatario.getId()));
    }

    @Operation(summary = "Modificar un destinatario, buscado por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Destinatario modificado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DestinatarioDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 12,
                                                    "nombre": "Banco Alimentos Coria",
                                                    "direccion": "C/Mairena",
                                                    "personaContacto": "Bartolom?? M??ndez",
                                                    "telefono": "987654321",
                                                    "kgTotales": 1.0,
                                                    "numCajas": [
                                                        4
                                                    ]
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Cuerpo para la modificaci??n aportado inv??lido",
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

                        return destinatarioService.generarDto(des);
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
        if(d1.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            destinatarioService.deleteById(id);
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @Operation(summary = "Obtiene la informaci??n de un destinatario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el destinatario relacionado con ese ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DestinatarioDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "kgTotales": 17.0,
                                                    "numeroCajas": 2
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado un destinatario con ese ID",
                    content = @Content),
    })
    @JsonView(DestinatarioViews.Simple.class)
    @GetMapping("/destinatario/{id}")
    public ResponseEntity<DestinatarioDto> findById(@PathVariable Long id) {
        Optional<DestinatarioDto> d1 = destinatarioService.findDestinatarioById(id);
        return d1.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.of(d1);
    }

    @Operation(summary = "Devolver?? la lista de los destinatarios m??s completa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha podido encontrar la lista de los destinatarios",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DestinatarioDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                    [
                                                        {
                                                            "id": 10,
                                                            "nombre": "Banco Alimentos Triana",
                                                            "direccion": "C/ Castilla, 10",
                                                            "personaContacto": "Antonio ??lvarez",
                                                            "telefono": "123456789",
                                                            "kgTotales": 17.0,
                                                            "numCajas": [
                                                                1,
                                                                2
                                                            ]
                                                        },
                                                        {
                                                            "id": 11,
                                                            "nombre": "Banco Alimentos Mairena",
                                                            "direccion": "C/Ciaurriz",
                                                            "personaContacto": "Bartolom?? M??ndez",
                                                            "telefono": "987654321",
                                                            "kgTotales": 4.0,
                                                            "numCajas": [
                                                                3
                                                            ]
                                                        },
                                                        {
                                                            "id": 12,
                                                            "nombre": "Banco Alimentos Sevilla",
                                                            "direccion": "Avda/Coria",
                                                            "personaContacto": "Jos?? P??rez",
                                                            "telefono": "456789123",
                                                            "kgTotales": 1.0,
                                                            "numCajas": [
                                                                4
                                                            ]
                                                        }
                                                    ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado una lista de destinatarios",
                    content = @Content
            )   ,
    })
    @GetMapping("/destinatario/")
    public ResponseEntity<List<DestinatarioDto>> findAll(Long id){

        List<DestinatarioDto> result = destinatarioService.findAll().stream()
                .map(destinatarioService::generarDto)
                .toList();

        return ResponseEntity
                .ok()
                .body(result);
    }

    @Operation(summary = "Devolver?? al destinatario espec??fico con los detalles completos de las cajas asignadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha podido encontrar al destinatario espec??fico",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DestinatarioDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                    {
                                                        "id": 12,
                                                        "nombre": "Banco Alimentos Sevilla",
                                                        "direccion": "Avda/Coria",
                                                        "personaContacto": "Jos?? P??rez",
                                                        "telefono": "456789123",
                                                        "kgTotales": 1.0,
                                                        "numCajas": [
                                                            4
                                                        ]
                                                    }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado al destinatario especificado",
                    content = @Content
            )   ,
    })
    @GetMapping("/destinatario/{id}/detalle")
    public ResponseEntity<DestinatarioDto> findByIdDestinatarioDetalles(@PathVariable Long id) {

        Optional<Destinatario> destinatario = destinatarioService.findById(id);

        if (destinatario.isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        } else {

            DestinatarioDto getDestinatarios = destinatarioService.generarDto(destinatario.get());

            List<CajaDto> getCajaDetalles = destinatario.get().getCajaList().stream()

                    .map(caja -> {
                        CajaDto getCaja = new CajaDto();
                        getCaja.setNumCaja(caja.getNumCaja());
                        getCaja.setKilosTotales(caja.getKilosTotales());

                        List<TipoAlimentoDto> alimentos = caja.getTieneList().stream()

                                .map(alimento -> {
                                    TipoAlimentoDto getDetalleAlimento = new TipoAlimentoDto();
                                    getDetalleAlimento.setNombre(alimento.getTipoAlimento().getNombre());
                                    getDetalleAlimento.setKilosEnviados(alimento.getCantidadKgs());

                                    return getDetalleAlimento;
                                })

                                .collect(Collectors.toList());

                        getCaja.setAlimentos(alimentos);

                        return getCaja;
                    })

                    .collect(Collectors.toList());
            
            return ResponseEntity
                    .ok()
                    .body(getDestinatarios);
        }
    }


}
