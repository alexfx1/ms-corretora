package com.corretora.api.controller;

import com.corretora.api.dto.ClienteDto;
import com.corretora.api.entity.Cliente;
import com.corretora.api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDto> save(@RequestBody ClienteDto clienteDto) {
        return ResponseEntity.ok(clienteService.save(clienteDto));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDto>> findAll() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/id/{cdCliente}")
    public ResponseEntity<ClienteDto> findById(@PathVariable Long cdCliente) {
        var cliente = clienteService.findById(cdCliente);
        if(cliente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Cliente>> findAll(@RequestParam(defaultValue = "0", required = false) int page,
                                                 @RequestParam(defaultValue = "10", required = false) int pageSize,
                                                 @RequestParam(required = false) String nome,
                                                 @RequestParam(required = false) String cpfCnpj,
                                                 @RequestParam(required = false) String endereco,
                                                 @RequestParam(required = false) String cidade,
                                                 @RequestParam(required = false) String estado) {
        var filter = new ClienteDto();
        filter.setDsNome(nome);
        filter.setCdCpfCnpj(cpfCnpj);
        filter.setDsEndereco(endereco);
        filter.setDsCidade(cidade);
        filter.setDsEstado(estado);
        return ResponseEntity.ok(clienteService.findAll(page, pageSize, filter));
    }
}
