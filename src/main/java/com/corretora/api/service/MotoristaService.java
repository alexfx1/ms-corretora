package com.corretora.api.service;

import com.corretora.api.dto.MotoristaDto;
import com.corretora.api.entity.Motorista;
import com.corretora.api.repository.MotoristaRepository;
import com.corretora.api.utils.MotoristaSpecification;
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
public class MotoristaService {
    @Autowired
    private MotoristaRepository motoristaRepository;

    public List<MotoristaDto> findAll() {
        return motoristaRepository.findAll(Sort.by("dsNome")).stream().map((motorista) -> {
            var dto = new MotoristaDto();
            BeanUtils.copyProperties(motorista, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public MotoristaDto save(MotoristaDto motoristaDto) {
        try {
            var response = saveEntity(motoristaDto);
            BeanUtils.copyProperties(response, motoristaDto);
            return motoristaDto;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "client not found");
        }
    }

    public Motorista saveEntity(MotoristaDto motoristaDto) {
        if(motoristaDto.getCdMotorista() != null) {
            var motorista = motoristaRepository.findById(motoristaDto.getCdMotorista()).orElseThrow();
            BeanUtils.copyProperties(motoristaDto, motorista);
            return motoristaRepository.save(motorista);
        } else {
            var novoMotorista = new Motorista();
            BeanUtils.copyProperties(motoristaDto, novoMotorista);
            return motoristaRepository.save(novoMotorista);
        }
    }

    public MotoristaDto findById(Long cdMotorista) {
        var motorista = motoristaRepository.findById(cdMotorista);
        if(motorista.isPresent()) {
            var dto = new MotoristaDto();
            BeanUtils.copyProperties(motorista.get(), dto);
            return dto;
        }
        return null;
    }

    public Page<Motorista> findAll(int page, int pageSize, MotoristaDto dto) {
        var filter = MotoristaSpecification.filter(dto);
        return motoristaRepository.findAll(filter, PageRequest.of(page, pageSize));
    }
}
