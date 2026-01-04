package com.corretora.api.controller;

import com.corretora.api.dto.MotoristaDto;
import com.corretora.api.entity.Motorista;
import com.corretora.api.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/motorista")
public class MotoristaController {
    @Autowired
    private MotoristaService motoristaService;

    @PostMapping
    public ResponseEntity<MotoristaDto> save(@RequestBody MotoristaDto motoristaDto) {
        return ResponseEntity.ok(motoristaService.save(motoristaDto));
    }

    @GetMapping
    public ResponseEntity<List<MotoristaDto>> findAll() {
        return ResponseEntity.ok(motoristaService.findAll());
    }

    @GetMapping("/id/{cdMotorista}")
    public ResponseEntity<MotoristaDto> findById(@PathVariable Long cdMotorista) {
        var motorista = motoristaService.findById(cdMotorista);
        if (motorista == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(motorista);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Motorista>> findAll(@RequestParam(defaultValue = "0", required = false) int page,
                                                   @RequestParam(defaultValue = "10", required = false) int pageSize,
                                                   @RequestParam(required = false) String nome,
                                                   @RequestParam(required = false) String placa,
                                                   @RequestParam(required = false) String cidade,
                                                   @RequestParam(required = false) String estado,
                                                   @RequestParam(required = false) String cpf) {
        var dto = new MotoristaDto(cpf, nome, placa, cidade, estado);
        return ResponseEntity.ok(motoristaService.findAll(page, pageSize, dto));
    }
}
