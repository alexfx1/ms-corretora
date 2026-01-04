package com.corretora.api.service;

import com.corretora.api.dto.MercadoriaDto;
import com.corretora.api.entity.Mercadoria;
import com.corretora.api.records.MercadoriaRecord;
import com.corretora.api.repository.MercadoriaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class MercadoriaService {

    @Autowired
    private MercadoriaRepository mercadoriaRepository;

    @Transactional
    public Mercadoria save(MercadoriaRecord mercadoriaRecord) {
        String nome = mercadoriaRecord.nome().trim().toUpperCase(Locale.ROOT);
        var mercadoria = mercadoriaRepository.findByDsMercadoria(nome);
        return mercadoria.orElseGet(() -> mercadoriaRepository.save(new Mercadoria(nome, mercadoriaRecord.flAtivo())));
    }

    public List<MercadoriaRecord> findAll() {
        return mercadoriaRepository.findAll().stream().map(mercadoria ->
                new MercadoriaRecord(mercadoria.getDsMercadoria(), mercadoria.getFlAtivo())).toList();
    }

    public MercadoriaDto findById(Long cdMercadoria) {
        var mercadoria = mercadoriaRepository.findById(cdMercadoria);
        if(mercadoria.isPresent()) {
            var dto = new MercadoriaDto();
            BeanUtils.copyProperties(mercadoria.get(), dto);
            return dto;
        }
        return null;
    }
}
