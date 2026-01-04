package com.corretora.api.utils;

import com.corretora.api.dto.MotoristaDto;
import com.corretora.api.entity.Motorista;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MotoristaSpecification {
    public static Specification<Motorista> filter(MotoristaDto dto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(dto.getDsNome() != null) {
                predicates.add(cb.like(cb.lower(root.get("dsNome")), "%" + dto.getDsNome().toLowerCase() + "%"));
            }
            if(dto.getDsPlaca() != null) {
                predicates.add(cb.like(cb.lower(root.get("dsPlaca")), "%" + dto.getDsPlaca().toLowerCase() + "%"));
            }
            if(dto.getDsCidade() != null) {
                predicates.add(cb.like(cb.lower(root.get("dsCidade")), "%" + dto.getDsCidade().toLowerCase() + "%"));
            }
            if(dto.getDsEstado() != null) {
                predicates.add(cb.like(cb.lower(root.get("dsEstado")), "%" + dto.getDsEstado().toLowerCase() + "%"));
            }
            if(dto.getCdCpf() != null) {
                predicates.add(cb.equal(root.get("cdCpf"), dto.getCdCpf()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
