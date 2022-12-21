package com.salesianostriana.dam.kiloapi.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.kiloapi.dto.TipoAlimento.CrearTipoAlimentoDto;
import com.salesianostriana.dam.kiloapi.dto.TipoAlimento.TipoAlimentoDto;
import com.salesianostriana.dam.kiloapi.dto.TipoAlimento.TipoAlimentoViews;
import com.salesianostriana.dam.kiloapi.model.KilosDisponibles;
import com.salesianostriana.dam.kiloapi.model.TipoAlimento;
import com.salesianostriana.dam.kiloapi.repository.KilosDisponiblesRepository;
import com.salesianostriana.dam.kiloapi.repository.TipoAlimentoRepository;
import com.salesianostriana.dam.kiloapi.service.AportacionService;
import com.salesianostriana.dam.kiloapi.service.CajaService;
import com.salesianostriana.dam.kiloapi.service.TieneService;
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

@RestController
@RequiredArgsConstructor
@Tag(name = "TipoAlimento", description = "Controlador para gestionar los tipos de alimentos")
public class TipoAlimentoController {

    private final TipoAlimentoService tipoAlimentoService;
    private final TipoAlimentoRepository repository;
    private final AportacionService aportacionService;
    private final CajaService cajaService;
    private final KilosDisponiblesRepository kilosDisponiblesRepository;


    @Operation(summary = "Obtiene todos los tipos de alimentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado todas las clases",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TipoAlimento.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 1,
                                                    "nombre": "Latas de conserva"
                                                },
                                                {
                                                    "id": 2,
                                                    "nombre": "Productos limpieza"
                                                }
                                             ]                                         
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningun tipo de alimento",
                    content = @Content),
    })
    @JsonView(TipoAlimentoViews.Master.class)
    @GetMapping("/tipoAlimento/")
    public ResponseEntity<List<TipoAlimentoDto>> getAll() {
        if (tipoAlimentoService.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(kilosDisponiblesRepository.crearTipoAlimentoDto());
        }
    }


    @Operation(summary = "Obtener detalles de un tipo de alimento por su ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tipo de alimento encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TipoAlimentoDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {"id": 2, "nombre": "Leche", "kilosDisponibles": 25.0}
                                            """
                            )}
                    )}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tipo de alimento no encontrado",
                    content = @Content)})
    @JsonView(TipoAlimentoViews.MostrarDisponible.class)
    @GetMapping("/tipoAlimento/{id}")
    public ResponseEntity<TipoAlimentoDto> mostrarDetallesTipo(@PathVariable Long id) {
        Optional<TipoAlimento> tipoAlimento = tipoAlimentoService.findById(id);
        if (tipoAlimento.isPresent()) {
            return ResponseEntity.ok(repository.detallesAlimento(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Agrega un nuevo tipo de alimento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha agregado el tipo de alimento",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TipoAlimento.class),
                            examples = {@ExampleObject(
                                    value = """
                                                                                        
                                                {
                                                    "id": 1,
                                                    "nombre": "Latas de conserva"
                                                }
                                                                                      
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha agregado el tipo de alimento",
                    content = @Content),
    })
    @JsonView(TipoAlimentoViews.MostrarDisponible.class)
    @PostMapping("/tipoAlimento/")
    public ResponseEntity<TipoAlimentoDto> newTipoAlimento(@RequestBody TipoAlimento tipoAlimento) {
        if (tipoAlimento.getNombre() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            tipoAlimentoService.add(tipoAlimento);
            KilosDisponibles kd = KilosDisponibles
                    .builder()
                    .tipoAlimento(tipoAlimento)
                    .build();
            kilosDisponiblesRepository.save(kd);

            return ResponseEntity.status(HttpStatus.CREATED).body(repository.detallesAlimento(tipoAlimento.getId()));
        }
    }


    @Operation(summary = "Modificar un tipo de alimento, buscado por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de alimento modificado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TipoAlimentoDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": 2,
                                                    "nombre": "Leche Semidesnatada",
                                                    "kilosDisponibles": 32.0
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400", description = "Cuerpo para la modificación aportado inválido",
                    content = @Content)})
    @JsonView(TipoAlimentoViews.MostrarDisponible.class)
    @PutMapping("/tipoAlimento/{id}")
    public ResponseEntity<TipoAlimentoDto> modificarTipo(@PathVariable Long id, @RequestBody CrearTipoAlimentoDto c) {
        Optional<TipoAlimento> tipoAlimento = tipoAlimentoService.findById(id);
        if (!tipoAlimento.isPresent() || c.getNombre() == "" || c.getCantidadDisponible() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.of(
                    tipoAlimento.map(ta -> {
                        ta.setNombre(c.getNombre());
                        ta.getKilosDisponibles().setCantidadDisponible(c.getCantidadDisponible());

                        tipoAlimentoService.edit(ta);

                        return repository.detallesAlimento(id);
                    }));
        }
    }


    @Operation(summary = "Eliminar un tipo de alimento, buscado por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de alimento eliminado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TipoAlimento.class),
                            examples = {@ExampleObject(
                                    value = """
                                                {}
                                            """
                            )})})})
    @DeleteMapping("/tipoAlimento/{id}")
    public ResponseEntity<?> borrarTipoAlimento(@PathVariable Long id) {
        Optional<TipoAlimento> tipoAlimento = tipoAlimentoService.findById(id);
        if (tipoAlimento.isPresent()) {
            TipoAlimento ta = tipoAlimento.get();
            if (!aportacionService.comprobarExistenciaEnAportacion(ta) && !cajaService.comprobarExistenciaEnCaja(ta)) {
                tipoAlimentoService.delete(ta);
            }
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
