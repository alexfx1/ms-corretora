package com.corretora.api.service;

import com.corretora.api.dto.ClienteDto;
import com.corretora.api.entity.Cliente;
import com.corretora.api.repository.ClienteRepository;
import com.corretora.api.utils.ClienteSpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public ClienteDto save(ClienteDto clienteDto) {
        try {
            var result = saveEntity(clienteDto);
            BeanUtils.copyProperties(result, clienteDto);
            return clienteDto;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found");
        }
    }

    public List<ClienteDto> findAll() {
        return clienteRepository.findAll(Sort.by("dsNome")).stream().map(cliente -> {
            var dto = new ClienteDto();
            BeanUtils.copyProperties(cliente, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    public Page<Cliente> findAll(int page, int pageSize, ClienteDto filter) {
        var pageRequest = PageRequest.of(page, pageSize);
        var specification = ClienteSpecification.filter(filter);
        return clienteRepository.findAll(specification, pageRequest);
    }

    public Cliente saveEntity(ClienteDto clienteDto) {
        if(clienteDto.getCdCliente() != null) {
            var cliente = clienteRepository.findById(clienteDto.getCdCliente()).orElseThrow();
            BeanUtils.copyProperties(clienteDto, cliente);
            return clienteRepository.save(cliente);
        } else {
            var novoCliente = new Cliente();
            BeanUtils.copyProperties(clienteDto, novoCliente);
            return clienteRepository.save(novoCliente);
        }
    }

    public ClienteDto findById(Long cdCliente) {
        var cliente = clienteRepository.findById(cdCliente);
        if(cliente.isPresent()) {
            var dto = new ClienteDto();
            BeanUtils.copyProperties(cliente.get(), dto);
            return dto;
        }
        return null;
    }

}
