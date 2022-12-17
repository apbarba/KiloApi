package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.dto.GetDestinatario;
import com.salesianostriana.dam.kiloapi.model.Destinatario;
import com.salesianostriana.dam.kiloapi.repository.DestinatarioRepository;
import com.salesianostriana.dam.kiloapi.service.DestinatarioService;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DestinatarioController {

    private final DestinatarioService destinatarioService;

    private final DestinatarioRepository destinatarioRepository;

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
}
