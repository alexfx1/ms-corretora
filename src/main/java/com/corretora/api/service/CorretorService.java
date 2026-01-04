package com.corretora.api.service;

import com.corretora.api.dto.CorretorDto;
import com.corretora.api.entity.Corretor;
import com.corretora.api.repository.CorretorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CorretorService {

    @Autowired
    private CorretorRepository corretorRepository;

    @Transactional
    public CorretorDto save(CorretorDto dto) {

        dto.setDsEstado(dto.getDsEstado().toUpperCase());

        if(dto.getCdCorretor() != null && dto.getCdCorretor() != 0) {
            var corretor = corretorRepository.findById(dto.getCdCorretor()).orElseThrow();
            BeanUtils.copyProperties(dto, corretor);
            corretorRepository.save(corretor);
        } else {
            var novoCorretor = new Corretor();
            BeanUtils.copyProperties(dto, novoCorretor);
            novoCorretor.setCdCorretor(null);
            corretorRepository.save(novoCorretor);
        }

        return dto;
    }

    public List<Corretor> findAll() {
        return corretorRepository.findAll();
    }
    
}
