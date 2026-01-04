package com.corretora.api.service;

import com.corretora.api.entity.Parametro;
import com.corretora.api.records.ParametroRecord;
import com.corretora.api.repository.ParametroRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ParametroService {
    @Autowired
    private ParametroRepository parametroRepository;

    @Transactional
    public Parametro save(ParametroRecord request) {
        var parametro = new Parametro();
        return update(request, parametro);
    }

    public Parametro getById(Long cdParametro) {
        return parametroRepository.findById(cdParametro).orElseThrow();
    }

    @Transactional
    public Parametro updateById(ParametroRecord request, String dsChaveParametro) {
        var param = findByKey(dsChaveParametro);
        return update(request, param);
    }

    public Page<Parametro> getAll(int page, int pageSize) {
        return parametroRepository.findAll(PageRequest.of(page, pageSize));
    }

    public List<Parametro> getAll() {
        return parametroRepository.findAll();
    }

    public Page<Parametro> search(String term, int page, int pageSize) {
        return parametroRepository.findByDsParametroOrVlParametro(term, term, PageRequest.of(page, pageSize));
    }

    public void delete(Long cdParametro) {
        var paramtro = getById(cdParametro);
        log.info("Excluindo parametro: {}", paramtro.getDsParametro());
        parametroRepository.delete(paramtro);
    }

    public Parametro findByKey(String dsChaveParametro) {
        return parametroRepository.findByDsChaveParametro(dsChaveParametro).orElseThrow();
    }

    private Parametro update(ParametroRecord request, Parametro parametro) {
        BeanUtils.copyProperties(request,parametro);
        return parametroRepository.save(parametro);
    }

}
