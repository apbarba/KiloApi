package com.salesianostriana.dam.kiloapi.controller;

import com.salesianostriana.dam.kiloapi.dto.AportacionDtoConverter;
import com.salesianostriana.dam.kiloapi.dto.GetAportacionDto;
import com.salesianostriana.dam.kiloapi.model.Aportacion;
import com.salesianostriana.dam.kiloapi.model.DetalleAportacion;
import com.salesianostriana.dam.kiloapi.repository.AportacionRepository;
import com.salesianostriana.dam.kiloapi.service.AportacionesService;
import com.salesianostriana.dam.kiloapi.service.ClaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AportacionController {

    private final AportacionRepository aportacionRepository;

    private final AportacionDtoConverter converterAportacion;

    private final AportacionesService aportacionesService;

    private final ClaseService claseService;

   // @GetMapping("/aportacion/{id}")
   // public ResponseEntity<List<GetAportacion>> findById(@PathVariable Long id) {

     //   if (aportacionesService.findById(id).isEmpty()) {

       //     return ResponseEntity
         //           .ok()
           //         .build();
        //}
       // List<GetAportacion> getAportacionList =
         //       aportacionesService.findById(id).stream()
           //             .map(converterAportacion::getAportacionDetallesDto)
             //           .collect(Collectors.toList());

        //return ResponseEntity
          //      .ok()
            //    .body(getAportacionList);
    //}

    @Operation(summary = "Obtener una lista con todas las aportaciones de una clase correspondiente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Clase Encontrada",
                    content = {@Content(mediaType = "aplication/json",
                        schema = @Schema(implementation = GetAportacionDto.class),
                    examples = {@ExampleObject(
                            value = """
                                    
                                    [Luego lo termino]
                                    
                                    """
                    )}

                    )}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Clase inexistente",
                    content = @Content)})
    @GetMapping("/aportacion/clase/{id}")
    public ResponseEntity<List<GetAportacionDto>> findAll(@PathVariable Long id) {

        List<Aportacion> lista = aportacionesService.getAportacionDto(id);

        if (lista.isEmpty()) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();

        } else {
            List<GetAportacionDto> listaAport = new ArrayList<>();

            for (Aportacion a:lista) {
                Map<String, Double> mapa = new HashMap<>();

                for (DetalleAportacion d: a.getDetalleAportacionList()) {
                    mapa.put(d.getTipoAlimento().getNombre(), d.getCantidadKg());
                }
                listaAport.add(converterAportacion.aportacionToGetAportacionDto(a, mapa));
            }

            return ResponseEntity.status(HttpStatus.OK).
                    body(listaAport);
        }
    }


    //@GetMapping("/aportacion/{idClase}")
   /*public ResponseEntity<List<GetAportacion>> getAportacionesByIdClase(@PathVariable Long idClase) {

        Optional<Clase> clase = claseService.findById(idClase);

        if (clase.isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        List<GetAportacion> aportacionList =
                clase.get().getAportacionList().stream()

                        .map(aportacion -> new GetAportacion(

                                aportacion.getFecha(),

                                aportacion.getDetalleAportacionList().stream()

                                        .map(detalleAportacion -> new AbstractMap.SimpleEntry<>

                                                (detalleAportacion.getTipoAlimento().getNombre(), detalleAportacion.getCantidadKg()))

                                        .collect(Collectors.toList())
                        ))

                        .collect(Collectors.toList());

        return ResponseEntity
                .ok()
                .body(aportacionList);


    }
    */

}
