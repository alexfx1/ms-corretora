package com.corretora.api.repository;

import com.corretora.api.entity.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long>, JpaSpecificationExecutor<Contrato> {
    List<Contrato> findByCdCorretor(Long cdCorretor);
    Page<Contrato> findByCdCorretorOrderByDtUpdateDesc(Long cdCorretor, Pageable pageable);
}
