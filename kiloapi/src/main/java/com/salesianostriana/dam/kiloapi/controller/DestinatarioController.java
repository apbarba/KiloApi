package com.salesianostriana.dam.kiloapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.Caja.CajaDto;
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

    private final CajaService cajaService;

    private final TipoAlimentoService tipoAlimentoService;

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

//            Destinatario dto = destinatarioRepository.crearDestinatarioDto(destinatario.getId());
        destinatarioService.add(destinatario);
        DestinatarioDto dto = DestinatarioDto.mostrarDatos(destinatario);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);


            /*        if (cajaDto.getNumCaja() == 0 || cajaDto.getQr() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Caja caja = Caja.of(cajaDto);
        caja = cajaService.add(caja);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CajaDto.of(caja));*/
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

    @Operation(summary = "Devolverá la lista de los destinatarios más completa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha podido encontrar la lista de los destinatarios",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Destinatario.class)),
                            examples = {@ExampleObject(

                                    value = """
                                    [Luego termino]
                                    """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado una lista de destinatarios",
                    content = @Content
            )   ,
    })
    //Método utilizado con la consulta realizada en el DestinatarioReposity
    @GetMapping("/destinatario/")
    public ResponseEntity<List<DestinatarioDto>> findAll(Long id){

        return ResponseEntity
                .ok()
                .body(destinatarioService.findAll(id));
    }

    @Operation(summary = "Devolverá al destinatario específico con los detalles completos de las cajas asignadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha podido encontrar al destinatario específico",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Destinatario.class)),
                            examples = {@ExampleObject(

                                    value = """
                                    {
                                    "id":1
                                    "nombre" : "Ana"
                                    "direccion" : "Calle Conde Bustillo"
                                    "telefono" : "111-111-111"
                                    "personaContacto" : "Lola Barba"
                                    "cajasDetalles" : [
                                       
                                        {
                                            "NumCaja" : 1,
                                            "kilosTotales" : 20.00
                                            "alimentos" : [
                                               
                                                {
                                                    "nombre" : "Legumbres",
                                                    "cantidadKilos" : 3.0
                                                }
                                            ]
                                        }
                                         
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

            DestinatarioDto getDestinatarios = new DestinatarioDto();

            getDestinatarios.setNombre(destinatario.get().getNombre());

            getDestinatarios.setDireccion(destinatario.get().getDireccion());

            getDestinatarios.setTelefono(destinatario.get().getTelefono());

            getDestinatarios.setPersonaContacto(destinatario.get().getPersonaContacto());

            List<CajaDto> cajaDetalles = destinatario.get().getCajaList().stream()

                    .map(caja -> {
                        CajaDto getCaja = new CajaDto();
                        getCaja.setNumCaja(caja.getNumCaja());
                        getCaja.setKilosTotales(caja.getKilosTotales());

                        List<TipoAlimentoDto> alimentos = caja.getTieneList().stream()

                                .map(alimento -> {
                                    TipoAlimentoDto getDetalleAlimento = new TipoAlimentoDto();
                                    getDetalleAlimento.setNombre(getDetalleAlimento.getNombre());
                                  //  getDetalleAlimento.getKilosEnviados(alimento.getCantidadKgs()); Kilos enviados es double mientras yo lo utilicé con un integer

                                    return getDetalleAlimento;
                                })

                                .collect(Collectors.toList());

                        getCaja.setAlimentos(alimentos);

                        return getCaja;
                    })

                    .collect(Collectors.toList());

            getDestinatarios.setCajas(cajaDetalles);

            return ResponseEntity
                    .ok()
                    .body(getDestinatarios);
        }
    }


}
