package com.example.chaosemeraldservice.controller;

import com.example.chaosemeraldservice.exception.EmeraldCreationFailedException;
import com.example.chaosemeraldservice.exception.EmeraldException;
import com.example.chaosemeraldservice.exception.EmeraldNotUpdatedException;
import com.example.chaosemeraldservice.exception.EmeraldUpdateFailedException;
import com.example.chaosemeraldservice.model.Emerald;
import com.example.chaosemeraldservice.service.EmeraldService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class EmeraldController {

    private static final String EMERALDS_URI = "/emeralds";

    private EmeraldService emeraldService;

    public EmeraldController(EmeraldService emeraldService) {
        this.emeraldService = emeraldService;
    }

    @GetMapping(value = EMERALDS_URI + "/{id}")
    public ResponseEntity<Emerald> getEmerald(@PathVariable Long id) throws EmeraldException {
        return ResponseEntity.ok(Optional.ofNullable(this.emeraldService.getEmerald(id))
                .orElseThrow(EmeraldException::new));
    }

    @GetMapping(value = EMERALDS_URI)
    public ResponseEntity<List<Emerald>> getAllEmeralds() throws EmeraldException {
        return ResponseEntity.ok(Optional.ofNullable(this.emeraldService.getEmeralds())
                .orElseThrow(EmeraldException::new));
    }

    @PostMapping(value = EMERALDS_URI)
    public ResponseEntity<Emerald> postEmerald(@RequestBody Emerald emerald) throws EmeraldException {
        return generateCreatedResponseEntity(
                Optional.ofNullable(this.emeraldService.createEmerald(
                        emerald.getPowerLevel(), emerald.getColour()))
                        .orElseThrow(EmeraldCreationFailedException::new));
    }

    @PutMapping(value = EMERALDS_URI + "/{id}")
    public ResponseEntity<Emerald> putEmerald(@PathVariable Long id, @RequestBody Emerald emerald) throws EmeraldException {
        if (!id.equals(emerald.getId())) {
            throw new EmeraldNotUpdatedException();
        }
        return generateCreatedResponseEntity(Optional.ofNullable(this.emeraldService.updateEmerald(emerald))
                .orElseThrow(EmeraldUpdateFailedException::new));
    }

    @DeleteMapping(value = EMERALDS_URI + "/{id}")
    public ResponseEntity deleteEmerald(@PathVariable Long id) throws EmeraldException {
        this.emeraldService.deleteEmerald(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Emerald> generateCreatedResponseEntity(Emerald emerald) {
        return ResponseEntity.created(URI.create(EMERALDS_URI + "/" + emerald.getId())).body(emerald);
    }
}
