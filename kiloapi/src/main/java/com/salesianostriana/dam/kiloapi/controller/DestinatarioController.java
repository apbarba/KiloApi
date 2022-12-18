package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.dto.GetCaja;
import com.salesianostriana.dam.kiloapi.dto.GetDestinatario;
import com.salesianostriana.dam.kiloapi.dto.GetDetalleAlimento;
import com.salesianostriana.dam.kiloapi.model.Caja;
import com.salesianostriana.dam.kiloapi.model.Destinatario;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class DestinatarioController {

    private final DestinatarioService destinatarioService;

    private final DestinatarioRepository destinatarioRepository;

    private final CajaService cajaService;

    private final TipoAlimentoService tipoAlimentoService;

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
    public ResponseEntity<List<GetDestinatario>> findAll(Long id){

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
                                            "NumCajas" : 1,
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
    public ResponseEntity<GetDestinatario> findByIdDestinatarioDetalles(@PathVariable Long id) {

        Optional<Destinatario> destinatario = destinatarioService.findById(id);

        if (destinatario.isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        } else {

            GetDestinatario getDestinatarios = new GetDestinatario();

            getDestinatarios.setNombre(destinatario.get().getNombre());

            getDestinatarios.setDireccion(destinatario.get().getDireccion());

            getDestinatarios.setTelefono(destinatario.get().getTelefono());

            getDestinatarios.setPersonaContacto(destinatario.get().getPersonaContacto());

            List<GetCaja> cajaDetalles = destinatario.get().getCajaList().stream()

                    .map(caja -> {
                        GetCaja getCaja = new GetCaja();
                        getCaja.setNumCajas(caja.getNumCaja());
                        getCaja.setKilosT(caja.getKilosTotales());

                        List<GetDetalleAlimento> alimentos = caja.getTieneList().stream()

                                .map(alimento -> {
                                    GetDetalleAlimento getDetalleAlimento = new GetDetalleAlimento();
                                    getDetalleAlimento.setNombre(getDetalleAlimento.getNombre());
                                    getDetalleAlimento.setKg(alimento.getCantidadKgs());

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
